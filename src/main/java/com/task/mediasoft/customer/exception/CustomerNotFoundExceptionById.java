package com.task.mediasoft.customer.exception;

import com.task.mediasoft.customer.model.Customer;

/**
 * Исключение, выбрасываемое при попытке найти клиента {@link Customer} по идентификатору, который не существует.
 */
public class CustomerNotFoundExceptionById extends RuntimeException {

    /**
     * Создает новый экземпляр исключения с указанным идентификатором клиента.
     *
     * @param id Идентификатор клиента, который не существует.
     */
    public CustomerNotFoundExceptionById(Long id) {
        super(String.format("Customer with id = '%s' not found", id));
    }
}