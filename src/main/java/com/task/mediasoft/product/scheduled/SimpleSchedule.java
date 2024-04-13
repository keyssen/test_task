package com.task.mediasoft.product.scheduled;

import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@RequiredArgsConstructor
@ConditionalOnExpression("'${app.scheduling.enabled}'.equals('true') and '${app.scheduling.optimization}'.equals('false')")
@Profile("prod")
public class SimpleSchedule {

    private final ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(SimpleSchedule.class);

    private final Environment env;

    @Scheduled(fixedDelay = 600000)
    @Transactional
    public void scheduleFixedDelayTask() {
        log.info("scheduleFixedDelayTask");
        Double priceIncreasePercentage = Double.parseDouble(env.getProperty("app.scheduling.priceIncreasePercentage"));
        final List<Product> productList = productRepository.findAll().stream()
                .map(product -> {
                    product.setPrice(product.getPrice() + priceIncreasePercentage);
                    return productRepository.save(product);
                }).toList();
    }
}
