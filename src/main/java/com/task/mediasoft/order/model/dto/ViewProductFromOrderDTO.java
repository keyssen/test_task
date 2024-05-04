package com.task.mediasoft.order.model.dto;

import com.task.mediasoft.orderProduct.model.OrderProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ViewProductFromOrderDTO {
    private final UUID productId;
    private final String name;
    private final Long quantity;
    private final BigDecimal price;

    public ViewProductFromOrderDTO(OrderProduct orderProduct) {
        this.productId = orderProduct.getProduct().getId();
        this.name = orderProduct.getProduct().getName();
        this.quantity = orderProduct.getQuantity();
        this.price = orderProduct.getFrozenPrice();
    }
}
