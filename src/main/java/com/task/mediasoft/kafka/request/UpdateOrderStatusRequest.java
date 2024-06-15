package com.task.mediasoft.kafka.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.mediasoft.order.model.dto.ChangeStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusRequest implements HttpEvent {

    private UUID orderId;

    private ChangeStatusDTO status;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.UPDATE_ORDER_STATUS;
    }
}
