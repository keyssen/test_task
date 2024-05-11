package com.task.mediasoft.order.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) для представления информации о заказе с продуктами.
 */
@Data
public class ViewOrderWithProductDTO {

    /**
     * Идентификатор заказа.
     */
    private final UUID orderId;

    /**
     * Список продуктов в заказе.
     */
    private final List<ViewProductFromOrderDTO> products;

    /**
     * Общая стоимость заказа.
     */
    private final BigDecimal totalPrice;
}