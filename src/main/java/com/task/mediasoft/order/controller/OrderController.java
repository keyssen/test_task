package com.task.mediasoft.order.controller;

import com.task.mediasoft.order.model.Order;
import com.task.mediasoft.order.model.dto.ChangeStatusDTO;
import com.task.mediasoft.order.model.dto.SaveOrderDTO;
import com.task.mediasoft.order.model.dto.SaveOrderProductDTO;
import com.task.mediasoft.order.model.dto.ViewOrderDTO;
import com.task.mediasoft.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ViewOrderDTO getOrderById(@PathVariable UUID id) {
        return new ViewOrderDTO(orderService.getOrderById(id));
    }

/*    @PostMapping
    public ViewOrderDTO createOrder(@Valid @RequestBody SaveOrderDTO orderDTO) {
        Order order = orderService.createOrder(orderDTO);
        return new ViewOrderDTO(orderService.getOrderById(order.getId()));
    }

    @PatchMapping("/{orderId}")
    public ViewOrderDTO updateOrder(@Valid @RequestBody List<SaveOrderProductDTO> products, @PathVariable UUID orderId) {
        Order order = orderService.updateOrder(products, orderId);
        return new ViewOrderDTO(orderService.getOrderById(order.getId()));
    }*/

    @PostMapping
    public Order createOrder(@Valid @RequestBody SaveOrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PatchMapping("/{orderId}")
    public Order updateOrder(@Valid @RequestBody List<SaveOrderProductDTO> products, @PathVariable UUID orderId) {
        return orderService.updateOrder(products, orderId);
    }

    @PatchMapping("/{orderId}/status")
    public ViewOrderDTO changeOrderStatus(@Valid @RequestBody ChangeStatusDTO changeStatusDTO, @PathVariable UUID orderId) {
        return new ViewOrderDTO(orderService.changeOrderStatus(orderId, changeStatusDTO));
    }

    @DeleteMapping("/{id}")
    public ViewOrderDTO deleteOrderById(@PathVariable UUID id) {
        return new ViewOrderDTO(orderService.deleteOrder(id));
    }
}
