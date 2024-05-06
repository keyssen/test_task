package com.task.mediasoft.order.model.dto;

import com.task.mediasoft.orderProduct.model.OrderProduct;
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

    /**
     * Создает новый объект ViewProductFromOrderDTO на основе сущности OrderProduct.
     *
     * @param orderProduct Сущность OrderProduct, из которой извлекаются данные для представления.
     */
    public ViewProductFromOrderDTO(OrderProduct orderProduct) {
        this.productId = orderProduct.getProduct().getId();
        this.name = orderProduct.getProduct().getName();
        this.quantity = orderProduct.getQuantity();
        this.price = orderProduct.getFrozenPrice();
    }
}