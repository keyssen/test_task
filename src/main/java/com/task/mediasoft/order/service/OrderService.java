package com.task.mediasoft.order.service;

import com.task.mediasoft.order.model.Order;
import com.task.mediasoft.order.model.dto.ChangeStatusDTO;
import com.task.mediasoft.order.model.dto.SaveOrderDTO;
import com.task.mediasoft.order.model.dto.SaveOrderProductDTO;
import com.task.mediasoft.order.model.dto.ViewOrderWithProductDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    ViewOrderWithProductDTO getViewOrderWithProductDTO(UUID id);

    Order createOrder(SaveOrderDTO createOrderDTO);

    Order updateOrder(List<SaveOrderProductDTO> products, UUID orderId);

    Order changeOrderStatus(UUID id, ChangeStatusDTO changeStatusDTO);

    Order deleteOrder(UUID id);
}