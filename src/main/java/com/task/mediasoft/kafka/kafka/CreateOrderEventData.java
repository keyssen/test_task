package com.task.mediasoft.kafka.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.mediasoft.kafka.request.Event;
import com.task.mediasoft.kafka.request.HttpEvent;
import com.task.mediasoft.order.model.dto.SaveOrderProductDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class CreateOrderEventData implements HttpEvent {

    private Long customerId;

    private String deliveryAddress;

    private List<SaveOrderProductDTO> products;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.CREATE_ORDER;
    }
}
