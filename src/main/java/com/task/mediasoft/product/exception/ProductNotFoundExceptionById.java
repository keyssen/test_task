package com.task.mediasoft.product.exception;

import java.util.UUID;

public class ProductNotFoundExceptionById extends RuntimeException{
    public ProductNotFoundExceptionById(UUID id) {
        super(String.format("Product with id = '%s' not found", id));
    }
}