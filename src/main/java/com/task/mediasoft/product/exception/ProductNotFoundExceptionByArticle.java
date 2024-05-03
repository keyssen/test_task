package com.task.mediasoft.product.exception;

import com.task.mediasoft.product.model.Product;

/**
 * Исключение, выбрасываемое при отсутствии продукта по его артикулу.
 * {@link Product} с указанным идентификатором не был найден.
 */
public class ProductNotFoundExceptionByArticle extends RuntimeException {
    /**
     * Конструктор для создания исключения ProductNotFoundExceptionByArticle с сообщением об ошибке.
     *
     * @param article Артикул продукта, который не был найден.
     */
    public ProductNotFoundExceptionByArticle(String article) {
        super(String.format("Product with article = '%s' not found", article));
    }
}