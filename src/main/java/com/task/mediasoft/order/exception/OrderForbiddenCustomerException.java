package com.task.mediasoft.order.exception;

/**
 * Исключение, выбрасываемое при попытке создания заказа клиентом без необходимых прав доступа.
 */
public class OrderForbiddenCustomerException extends RuntimeException {

    /**
     * Создает новый экземпляр исключения с информацией об ошибке, возникшей из-за отсутствия прав доступа у клиента.
     *
     * @param customerId Идентификатор клиента, вызвавшего ошибку.
     */
    public OrderForbiddenCustomerException(Long customerId) {
        super(String.format("When creating an order, an error occurred because the customer with id = '%s' has no access rights", customerId));
    }
}