package com.task.mediasoft.kafka.hadler;

import com.task.mediasoft.kafka.request.Event;
import com.task.mediasoft.kafka.request.EventSource;
import com.task.mediasoft.kafka.request.UpdateOrderStatusRequest;
import com.task.mediasoft.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateOrderStatusHandler implements EventHandler<UpdateOrderStatusRequest>  {

    private final OrderService orderService;

    @Override
    public boolean canHandle(final EventSource eventSource) {
        return Event.UPDATE_ORDER_STATUS.equals(eventSource.getEvent());
    }

    @Override
    public void handleEvent(final UpdateOrderStatusRequest eventSource) {
        orderService.changeOrderStatus(eventSource.getOrderId(), eventSource.getStatus());
    }
}
