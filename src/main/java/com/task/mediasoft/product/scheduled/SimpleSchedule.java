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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Класс SimpleSchedule представляет простое расписание для обновления цен продуктов.
 * Этот компонент выполняется только в профиле "prod" и при условии,
 * что параметры app.scheduling.enabled и app.scheduling.optimization установлены в "true" и "false" соответственно.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnExpression("${app.scheduling.enabled:false} and !${app.scheduling.optimization:false}")
@Profile("prod")
public class SimpleSchedule {
    /**
     * Репозиторий для доступа к данным о продуктах
     */
    private final ProductRepository productRepository;

    /**
     * Процент увеличения цены продукта
     */
    @Value("#{new java.math.BigDecimal('${app.scheduling.priceIncreasePercentage:10}')}")
    private BigDecimal priceIncreasePercentage;

    /**
     * Запланированное задание для обновления цен продуктов.
     */
    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    @Transactional
    @MeasureExecutionTime
    public void scheduleFixedDelayTask() {
        log.info("START SimpleSchedule");
        final List<Product> productList = productRepository.findAll();
        productList.forEach(product -> product.setPrice(getNewPrice(product.getPrice(), priceIncreasePercentage)));
        productRepository.saveAll(productList);
        log.info("END SimpleSchedule");
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
