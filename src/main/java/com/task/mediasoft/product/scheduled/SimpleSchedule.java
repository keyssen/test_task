package com.task.mediasoft.product.scheduled;

import com.task.mediasoft.annotation.MeasureExecutionTime;
import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Класс SimpleSchedule представляет простое расписание для обновления цен продуктов.
 * Этот компонент выполняется только в профиле "prod" и при условии,
 * что параметры app.scheduling.enabled и app.scheduling.optimization установлены в "true" и "false" соответственно.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnExpression("'${app.scheduling.enabled}'.equals('true') and '${app.scheduling.optimization}'.equals('false')")
@Profile("prod")
public class SimpleSchedule {


    /**
     * Репозиторий для доступа к данным о продуктах
     */
    private final ProductRepository productRepository;

    /**
     * Процент увеличения цены продукта
     */
    @Value("${app.scheduling.priceIncreasePercentage}")
    private double priceIncreasePercentage;

    /**
     * Запланированное задание для обновления цен продуктов.
     */
    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    @Transactional
    @MeasureExecutionTime
    public void scheduleFixedDelayTask() {
        log.info("START SimpleSchedule");
        final List<Product> productList = productRepository.findAll().stream()
                .map(product -> {
                    product.setPrice(product.getPrice() * (1 + priceIncreasePercentage / 100));
                    return product;
                }).toList();
        productRepository.saveAll(productList);
        log.info("END SimpleSchedule");
    }
}
