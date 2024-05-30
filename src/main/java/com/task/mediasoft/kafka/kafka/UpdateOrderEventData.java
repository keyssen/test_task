package com.task.mediasoft.kafka.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.mediasoft.kafka.request.Event;
import com.task.mediasoft.order.model.dto.SaveOrderProductDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UpdateOrderEventData implements KafkaEvent {

    private Long customerId;

    private UUID orderId;

    private List<SaveOrderProductDTO> products;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Prevents duplication when serializing to JSON (subtype discriminator property)
    public Event getEvent() {
        return Event.UPDATE_ORDER;
    }
}
