package com.task.mediasoft.kafka.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.mediasoft.order.model.dto.SaveOrderProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest implements HttpEvent {

    private Long customerId;

    private String deliveryAddress;

    private List<SaveOrderProductDTO> products;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.CREATE_ORDER;
    }
}
