package com.task.mediasoft.order.exception;


import com.task.mediasoft.order.model.Order;

import java.util.UUID;

/**
 * Исключение, выбрасываемое при отсутствии продукта по его артикулу.
 * {@link Order} с указанным идентификатором не был найден.
 */
public class OrderNotFoundExceptionById extends RuntimeException {
    /**
     * Конструктор для создания исключения OrderNotFoundExceptionById с сообщением об ошибке.
     *
     * @param id Идентификтаор продукта, который не был найден.
     */
    public OrderNotFoundExceptionById(UUID id) {
        super(String.format("Order with id = '%s' not found", id));
    }
}