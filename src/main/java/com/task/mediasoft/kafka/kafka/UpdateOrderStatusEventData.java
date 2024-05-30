package com.task.mediasoft.kafka.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.mediasoft.kafka.request.Event;
import com.task.mediasoft.order.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class UpdateOrderStatusEventData implements KafkaEvent {

    private UUID orderId;

    private OrderStatus status;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Prevents duplication when serializing to JSON (subtype discriminator property)
    public Event getEvent() {
        return Event.UPDATE_ORDER_STATUS;
    }
}
