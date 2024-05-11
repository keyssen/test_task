package com.task.mediasoft.order.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) для представления информации о продукте в заказе.
 */
@Data
public class ViewProductFromOrderDTO {

    /**
     * Идентификатор продукта.
     */
    private final UUID productId;

    /**
     * Наименование продукта.
     */
    private final String name;

    /**
     * Количество продукта в заказе.
     */
    private final Long quantity;

    /**
     * Цена продукта.
     */
    private final BigDecimal price;

    public ViewProductFromOrderDTO(UUID productId, String name, Long quantity, BigDecimal price) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}