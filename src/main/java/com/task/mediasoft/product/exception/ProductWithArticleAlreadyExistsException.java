package com.task.mediasoft.product.exception;

import com.task.mediasoft.product.model.Product;

/**
 * Исключение, выбрасываемое если продукт с данным артикулом уже существует.
 * {@link Product} с таким артикулом уже существует.
 */
public class ProductWithArticleAlreadyExistsException extends RuntimeException {
    /**
     * Конструктор для создания исключения ProductWithArticleAlreadyExistsException с сообщением об ошибке.
     *
     * @param article артикул продукта, который уже существует.
     */
    public ProductWithArticleAlreadyExistsException(String article) {
        super(String.format("Product with article = '%s' already exists", article));
    }
}