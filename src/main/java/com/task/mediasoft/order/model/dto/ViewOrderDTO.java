package com.task.mediasoft.order.model.dto;

import com.task.mediasoft.order.model.Order;
import com.task.mediasoft.order.model.OrderStatus;
import lombok.Data;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) для представления информации о заказе.
 */
@Data
public class ViewOrderDTO {

    /**
     * Идентификатор заказа.
     */
    private final UUID id;

    /**
     * Идентификатор клиента, сделавшего заказ.
     */
    private final Long customerId;

    /**
     * Статус заказа.
     */
    private final OrderStatus status;

    /**
     * Адрес доставки заказа.
     */
    private final String deliveryAddress;

    /**
     * Создает новый объект ViewOrderDTO на основе сущности Order.
     *
     * @param order Сущность Order, из которой извлекаются данные для представления.
     */
    public ViewOrderDTO(Order order) {
        this.id = order.getId();
        this.customerId = order.getCustomer().getId();
        this.status = order.getStatus();
        this.deliveryAddress = order.getDeliveryAddress();
    }
}