package com.task.mediasoft.kafka.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.mediasoft.kafka.request.Event;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeleteOrderEventData implements KafkaEvent {

    private UUID orderId;

    private Long customerId;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.CREATE_ORDER;
    }

}
