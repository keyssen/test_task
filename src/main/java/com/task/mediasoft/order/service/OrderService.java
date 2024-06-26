package com.task.mediasoft.order.service;

import com.task.mediasoft.order.model.Order;
import com.task.mediasoft.order.model.dto.ChangeStatusDTO;
import com.task.mediasoft.order.model.dto.SaveOrderDTO;
import com.task.mediasoft.order.model.dto.SaveOrderProductDTO;
import com.task.mediasoft.order.model.dto.ViewOrderWithProductDTO;
import com.task.mediasoft.product.controller.model.OrderInfo;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {
    Order getOrderById(UUID id);

    ViewOrderWithProductDTO getViewOrderWithProductDTO(UUID id, Long customerId);

    Map<UUID, List<OrderInfo>> getProductsInfo();

    Order createOrder(SaveOrderDTO createOrderDTO, Long customerId);

    Order updateOrder(List<SaveOrderProductDTO> products, UUID orderId, Long customerId);

    Order changeOrderStatus(UUID id, ChangeStatusDTO changeStatusDTO);

    Order deleteOrder(UUID id, Long customerId);
}