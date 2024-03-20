package com.task.mediasoft.product.exception;

public class ProductNotFoundExceptionByArticle extends RuntimeException{
    public ProductNotFoundExceptionByArticle(String article) {
        super(String.format("Product with article = '%s' not found", article));
    }
}