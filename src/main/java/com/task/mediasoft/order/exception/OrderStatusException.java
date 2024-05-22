package com.task.mediasoft.order.exception;

import com.task.mediasoft.order.model.OrderStatus;

/**
 * Исключение, выбрасываемое в случае ошибки связанной со статусом заказа.
 */
public class OrderStatusException extends RuntimeException {

    /**
     * Создает новый экземпляр исключения с информацией об ошибке, возникшей из-за недопустимого статуса заказа.
     *
     * @param orderStatus Недопустимый статус заказа.
     */
    public OrderStatusException(OrderStatus orderStatus) {
        super(String.format("An error occurred while creating an order because the order status is not equal to '%s'", orderStatus));
    }

    /**
     * Создает новый экземпляр исключения без указания конкретного статуса заказа.
     */
    public OrderStatusException() {
        super(String.format("Error in order status"));
    }
}