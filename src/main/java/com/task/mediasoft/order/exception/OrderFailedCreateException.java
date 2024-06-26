package com.task.mediasoft.order.exception;

import java.util.UUID;

/**
 * Исключение, выбрасываемое в случае ошибки при создании заказа.
 */
public class OrderFailedCreateException extends RuntimeException {

    /**
     * Создает новый экземпляр исключения с информацией об ошибке при создании заказа из-за конкретного продукта.
     *
     * @param productId       Идентификатор продукта, вызвавшего ошибку.
     * @param isAvailable     Флаг, указывающий, доступен ли продукт.
     * @param productQuantity Запрошенное количество продукта.
     * @param actualQuantity  Фактическое количество доступного продукта.
     */
    public OrderFailedCreateException(UUID productId, Boolean isAvailable, Long productQuantity, Long actualQuantity) {
        super(String.format("Product with id = '%s', isAvailable = '%s', quantity = '%s', actualQuantity = '%s' has caused an error", productId, isAvailable, productQuantity, actualQuantity));
    }
}