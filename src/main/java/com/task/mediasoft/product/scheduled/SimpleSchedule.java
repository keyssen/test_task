package com.task.mediasoft.product.scheduled;

import com.task.mediasoft.annotation.MeasureExecutionTime;
import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnExpression("'${app.scheduling.enabled}'.equals('true') and '${app.scheduling.optimization}'.equals('false')")
@Profile("prod")
public class SimpleSchedule {

    private final ProductRepository productRepository;

    private final Environment env;

    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    @Transactional
    @MeasureExecutionTime
    public void scheduleFixedDelayTask() {
        log.info("START SimpleSchedule");
        Double priceIncreasePercentage = Double.parseDouble(env.getProperty("app.scheduling.priceIncreasePercentage"));
        final List<Product> productList = productRepository.findAll().stream()
                .map(product -> {
                    product.setPrice(product.getPrice() * (1 + priceIncreasePercentage / 100));
                    return product;
                }).toList();
        productRepository.saveAll(productList);
        log.info("END SimpleSchedule");
    }
}
