package com.task.mediasoft.order.controller;

import com.task.mediasoft.order.model.dto.SaveOrderDTO;
import com.task.mediasoft.order.model.dto.SaveOrderProductDTO;
import com.task.mediasoft.order.model.dto.ViewOrderDTO;
import com.task.mediasoft.order.service.OrderService;
import com.task.mediasoft.orderProduct.model.OrderProduct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{productId}/{orderId}")
    public OrderProduct getOrderProductById(@PathVariable UUID productId, @PathVariable UUID orderId) {
        return orderService.getOrderProductById(productId, orderId);
    }

    @PostMapping
    public ViewOrderDTO createOrder(@Valid @RequestBody SaveOrderDTO orderDTO) {
        return new ViewOrderDTO(orderService.createOrder(orderDTO));
    }

    @PatchMapping("/{orderId}")
    public ViewOrderDTO updateOrder(@Valid @RequestBody List<SaveOrderProductDTO> products, @PathVariable UUID orderId) {
        return new ViewOrderDTO(orderService.updateOrder(products, orderId));
    }
}
