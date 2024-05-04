package com.task.mediasoft.order.model.dto;

import com.task.mediasoft.order.model.Order;
import com.task.mediasoft.orderProduct.model.OrderProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ViewOrderDTO {

    private final UUID orderId;

    private final List<ViewProductFromOrderDTO> products;

    private final BigDecimal totalPrice;

    public ViewOrderDTO(Order order) {
        this.orderId = order.getId();
        this.products = order.getOrderProducts().stream().map(ViewProductFromOrderDTO::new).toList();
        this.totalPrice = order.getOrderProducts().stream()
                .map(OrderProduct::getFrozenPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
