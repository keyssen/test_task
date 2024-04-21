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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@ConditionalOnExpression("${app.scheduling.enabled:false} and ${app.scheduling.optimization:false}")
@Profile("prod")
public class OptimizedSchedule {
    /**
     * Процент увеличения цены продукта
     */
    @Value("#{new java.math.BigDecimal('${app.scheduling.priceIncreasePercentage:10}')}")
    private BigDecimal priceIncreasePercentage;

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
     */
    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    @MeasureExecutionTime
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void scheduleFixedDelayTask() {
        try (Connection connection = dataSource.getConnection()) {
            log.info("OptimizedSchedule: Start.");
            connection.setAutoCommit(false);

            String selectQuery = "SELECT * FROM product FOR UPDATE";
            String updateQuery = "UPDATE product SET price = ? WHERE id = ?";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("products.txt"))) {
                Statement selectStatement = connection.createStatement();
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                ResultSet resultSet = selectStatement.executeQuery(selectQuery);

                int columnCount = resultSet.getMetaData().getColumnCount();
                int count = 0;
                while (resultSet.next()) {
                    UUID id = (UUID) resultSet.getObject("id");
                    BigDecimal currentPrice = resultSet.getBigDecimal("price");
                    BigDecimal newPrice = getNewPrice(currentPrice, priceIncreasePercentage);

                    writer.write(productToString(resultSet, columnCount));
                    writer.newLine();
                    updateStatement.setBigDecimal(1, newPrice);
                    updateStatement.setObject(2, id);
                    updateStatement.addBatch();
                    count++;
                    if (count % BATCH_SIZE == 0) {
                        updateStatement.executeBatch();
                    }
                }

                updateStatement.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Создает строковое представление продукта на основе переданного ResultSet.
     * Этот метод конкатенирует значения столбцов из ResultSet в одну строку,
     * разделенную запятыми.
     *
     * @param resultSet   ResultSet, содержащий данные о продукте.
     * @param columnCount Количество столбцов в ResultSet.
     * @return Строковое представление продукта.
     * @throws SQLException Если происходит ошибка в базе данных.
     */
    private String productToString(ResultSet resultSet, int columnCount) throws SQLException {
        StringBuilder row = new StringBuilder();
        for (int i = 1; i <= columnCount; i++) {
            if (i == columnCount) {
                row.append(resultSet.getString(i));
            } else {
                row.append(resultSet.getString(i)).append(", ");
            }
        }
        return row.toString();
    }

    /**
     * Вычисляет новую цену на основе старой цены и процентного повышения.
     *
     * @param oldPrice Старая цена продукта.
     * @param increase Процентное повышение.
     * @return Новая цена после применения повышения.
     */
    private BigDecimal getNewPrice(BigDecimal oldPrice, BigDecimal increase) {
        return oldPrice.multiply(BigDecimal.ONE.add(increase.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)));
    }
}