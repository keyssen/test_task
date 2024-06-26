package com.task.mediasoft.kafka.hadler;

import com.task.mediasoft.kafka.request.CreateOrderRequest;
import com.task.mediasoft.kafka.request.Event;
import com.task.mediasoft.kafka.request.EventSource;
import com.task.mediasoft.order.model.dto.SaveOrderDTO;
import com.task.mediasoft.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateOrderHandler implements EventHandler<CreateOrderRequest> {

    private final OrderService orderService;

    @Override
    public boolean canHandle(final EventSource eventSource) {
        return Event.CREATE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public void handleEvent(final CreateOrderRequest eventSource) {
        orderService.createOrder(new SaveOrderDTO(eventSource.getDeliveryAddress(), eventSource.getProducts()), eventSource.getCustomerId());
    }
}
