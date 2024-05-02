package com.task.mediasoft.order.model.dto;

import com.task.mediasoft.order.model.Order;
import com.task.mediasoft.order.model.StatusEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class ViewOrderDTO {

    private final UUID id;

    private final Long customerId;

    private final StatusEnum status;

    private final String deliveryAddress;

    public ViewOrderDTO(Order order) {
        this.id = order.getId();
        this.customerId = order.getCustomerId();
        this.status = order.getStatus();
        this.deliveryAddress = order.getDeliveryAddress();
    }
}
