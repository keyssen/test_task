package com.task.mediasoft.configuration;


import com.task.mediasoft.product.model.Product;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

/**
 * Класс ProductItemProcessor реализует интерфейс ItemProcessor
 * для обработки объектов типа Product.
 * Он увеличивает цену продукта на определенный процент, указанный в конфигурации.
 */
public class ProductItemProcessor implements ItemProcessor<Product, Product> {

    /**
     * Процент увеличения цены продукта
     */
    @Value("${app.scheduling.priceIncreasePercentage}")
    private String percent;

    /**
     * Обрабатывает каждый продукт, увеличивая его цену на указанный процент.
     *
     * @param product Продукт для обработки.
     * @return Обработанный продукт с обновленной ценой.
     * @throws Exception если возникают ошибки при обработке продукта.
     */
    @Override
    public Product process(Product product) throws Exception {
        double currentPrice = product.getPrice();
        double newPrice = currentPrice * (1 + Double.parseDouble(percent) / 100);
        product.setPrice(newPrice);
        return product;
    }
}