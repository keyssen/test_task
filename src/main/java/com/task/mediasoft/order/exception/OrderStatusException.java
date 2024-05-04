package com.task.mediasoft.order.exception;

import com.task.mediasoft.order.model.OrderStatus;

public class OrderStatusException extends RuntimeException {
    /**
     * Конструктор для создания исключения OrderNotFoundExceptionById с сообщением об ошибке.
     *
     * @param id Идентификтаор продукта, который не был найден.
     */

    public OrderStatusException(OrderStatus orderStatus) {
        super(String.format("An error occurred while creating an order because the order status is not equal to '%s'", orderStatus));
    }

    public OrderStatusException() {
        super(String.format("Error in order status"));
    }
}