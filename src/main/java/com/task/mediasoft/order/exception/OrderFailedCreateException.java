package com.task.mediasoft.order.exception;

import java.util.UUID;

public class OrderFailedCreateException extends RuntimeException {
    /**
     * Конструктор для создания исключения OrderNotFoundExceptionById с сообщением об ошибке.
     *
     * @param id Идентификтаор продукта, который не был найден.
     */
    public OrderFailedCreateException(UUID productId, Boolean isAvailable, Long productQuantity, Long actualQuantity) {
        super(String.format("Product with id = '%s', isAvailable = '%s', quantity = '%s', actualQuantity = '%s' has caused an error", productId, isAvailable, productQuantity, actualQuantity));
    }

    public OrderFailedCreateException() {
        super(String.format("An error occurred while creating an order"));
    }
}