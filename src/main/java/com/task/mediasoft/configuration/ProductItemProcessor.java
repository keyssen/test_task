package com.task.mediasoft.configuration;


import com.task.mediasoft.product.model.Product;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

public class ProductItemProcessor implements ItemProcessor<Product, Product> {
    @Value("${app.scheduling.priceIncreasePercentage}")
    private String percent;

    @Override
    public Product process(Product product) throws Exception {
        double currentPrice = product.getPrice();
        double newPrice = currentPrice * (1 + Double.parseDouble(percent) / 100);
        product.setPrice(newPrice);
        return product;
    }
}