package com.task.mediasoft.util;

import com.task.mediasoft.product.model.dto.SaveProductDTO;
import com.task.mediasoft.product.repository.ProductRepository;
import com.task.mediasoft.product.scheduled.SimpleSchedule;
import com.task.mediasoft.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("prod")
public class DatabaseInitializer implements CommandLineRunner {

    private final ProductService productService;

    private final ProductRepository productRepository;

    private static final Logger log = LoggerFactory.getLogger(SimpleSchedule.class);

    @Override
    public void run(String... args) {
        log.info("Initializing database..." + productRepository.count());
        if (productRepository.count() == 0) {
            for (int i = 0; i < 1_000_000; i++) {
                final SaveProductDTO saveProductDTO = new SaveProductDTO();
                saveProductDTO.setArticle("Article" + i);
                saveProductDTO.setCategory("Category" + i);
                saveProductDTO.setDescription("Description" + i);
                saveProductDTO.setQuantity(10);
                saveProductDTO.setPrice(10.0);
                saveProductDTO.setName("Product" + i);
                productService.createProduct(saveProductDTO);
            }
        }
    }
}