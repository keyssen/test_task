package com.task.mediasoft.order.exception;

public class OrderForbiddenCustomerException extends RuntimeException {
    public OrderForbiddenCustomerException(Long customerId) {
        super(String.format("When creating an order, an error occurred because the customer with id = '%s' has no access rights", customerId));
    }
}