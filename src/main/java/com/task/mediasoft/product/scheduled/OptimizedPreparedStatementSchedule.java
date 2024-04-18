package com.task.mediasoft.product.scheduled;

import com.task.mediasoft.annotation.MeasureExecutionTime;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Класс OptimizedPreparedStatementSchedule предоставляет запланированную задачу
 * для оптимизированного обновления цен продуктов с использованием PreparedStatement.
 * Он активируется только в профиле "prod" и при условии,
 * что параметры app.scheduling.enabled, app.scheduling.optimization и
 * app.scheduling.preparedStatement установлены в "true".
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnExpression("'${app.scheduling.enabled}'.equals('true') and '${app.scheduling.optimization}'.equals('true') and '${app.scheduling.preparedStatement}'.equals('true')")
@Profile("prod")
public class OptimizedPreparedStatementSchedule {

    /**
     * Процент увеличения цены продукта
     */
    @Value("${app.scheduling.priceIncreasePercentage}")
    private double priceIncreasePercentage;

    /**
     * Источник данных для управления подключением к базе данных
     */
    private final DataSource dataSource;

    /**
     * Размер пакета для пакетной обработки данных
     */
    private static final int BATCH_SIZE = 100000;

    /**
     * Запланированная задача для обновления цен продуктов.
     *
     * @throws SQLException если возникают проблемы с доступом к базе данных.
     * @throws IOException  если возникают проблемы с записью в файл.
     */
    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    @MeasureExecutionTime
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void scheduleFixedDelayTask() throws SQLException, IOException {
        try (Connection connection = dataSource.getConnection()) {
            log.info("OptimizedSchedule: Start.");
            BufferedWriter writer = new BufferedWriter(new FileWriter("products.txt"));
            // отключаем автокоммит, чтобы управлять транзакцией явно
            connection.setAutoCommit(false);

//            String selectQuery = "SELECT * FROM product FOR UPDATE";
            String selectQuery = "SELECT * FROM product";
            String updateQuery = "UPDATE product SET price = ? WHERE id = ?";


            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                 PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

                ResultSet resultSet = selectStatement.executeQuery();
                int columnCount = resultSet.getMetaData().getColumnCount();
                int count = 0;
                while (resultSet.next()) {
                    UUID id = (UUID) resultSet.getObject("id");
                    double currentPrice = resultSet.getDouble("price");
                    double newPrice = currentPrice * (1 + priceIncreasePercentage / 100);

                    StringBuilder row = new StringBuilder();
                    for (int i = 1; i <= columnCount; i++) {
                        if (i == columnCount) {
                            row.append(resultSet.getString(i));
                        } else {
                            row.append(resultSet.getString(i)).append(", ");
                        }
                    }
                    writer.write(row.toString());
                    writer.newLine();
                    updateStatement.setDouble(1, newPrice);
                    updateStatement.setObject(2, id);
                    updateStatement.addBatch();
                    count++;
                    // Если достигнут размер пакета, выполнить пакетное обновление
                    if (count % BATCH_SIZE == 0) {
                        updateStatement.executeBatch();
                    }
                }

                updateStatement.executeBatch();
                // фиксируем транзакцию
                connection.commit();
            } catch (SQLException | IOException e) {
                // откатываем транзакцию в случае исключения
                connection.rollback();
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
                writer.close();
            }
        }
    }
}