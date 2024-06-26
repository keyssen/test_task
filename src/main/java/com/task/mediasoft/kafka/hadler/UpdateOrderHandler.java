package com.task.mediasoft.kafka.hadler;

import com.task.mediasoft.kafka.request.Event;
import com.task.mediasoft.kafka.request.EventSource;
import com.task.mediasoft.kafka.request.UpdateOrderRequest;
import com.task.mediasoft.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateOrderHandler implements EventHandler<UpdateOrderRequest>  {

    private final OrderService orderService;

    @Override
    public boolean canHandle(final EventSource eventSource) {
        return Event.UPDATE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public void handleEvent(final UpdateOrderRequest eventSource) {
        orderService.updateOrder(eventSource.getProducts(), eventSource.getOrderId(), eventSource.getCustomerId());
    }
}
