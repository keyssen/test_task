package com.task.mediasoft.customer.exception;

import com.task.mediasoft.customer.model.Customer;

/**
 * Исключение, выбрасываемое при отсутствии продукта по его артикулу.
 * {@link Customer} с указанным идентификатором не был найден.
 */
public class CustomerNotFoundExceptionById extends RuntimeException {
    /**
     * Конструктор для создания исключения CustomerNotFoundExceptionById с сообщением об ошибке.
     *
     * @param id Идентификтаор продукта, который не был найден.
     */
    public CustomerNotFoundExceptionById(Long id) {
        super(String.format("Customer with id = '%s' not found", id));
    }
}