package com.task.mediasoft.order.exception;


import java.util.UUID;

/**
 * Исключение, выбрасываемое при попытке найти заказ по идентификатору, который не существует.
 */
public class OrderNotFoundExceptionById extends RuntimeException {

    /**
     * Создает новый экземпляр исключения с указанным идентификатором заказа.
     *
     * @param id Идентификатор заказа, который не найден.
     */
    public OrderNotFoundExceptionById(UUID id) {
        super(String.format("Order with id = '%s' not found", id));
    }
}