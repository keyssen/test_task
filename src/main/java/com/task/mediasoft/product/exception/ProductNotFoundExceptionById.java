package com.task.mediasoft.product.exception;

import com.task.mediasoft.product.model.Product;

import java.util.UUID;

/**
 * Исключение, выбрасываемое при отсутствии продукта по его артикулу.
 * {@link Product} с указанным идентификатором не был найден.
 */
public class ProductNotFoundExceptionById extends RuntimeException {
    /**
     * Конструктор для создания исключения ProductNotFoundExceptionByArticle с сообщением об ошибке.
     *
     * @param id Идентификтаор продукта, который не был найден.
     */
    public ProductNotFoundExceptionById(UUID id) {
        super(String.format("Product with id = '%s' not found", id));
    }
}