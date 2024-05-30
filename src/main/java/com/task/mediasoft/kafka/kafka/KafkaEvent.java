package com.task.mediasoft.kafka.kafka;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.task.mediasoft.kafka.request.CreateOrderRequest;
import com.task.mediasoft.kafka.request.DeleteOrderRequest;
import com.task.mediasoft.kafka.request.EventSource;
import com.task.mediasoft.kafka.request.UpdateOrderRequest;
import com.task.mediasoft.kafka.request.UpdateOrderStatusRequest;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "event"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateOrderRequest.class, name = "CREATE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderRequest.class, name = "UPDATE_ORDER"),
        @JsonSubTypes.Type(value = DeleteOrderRequest.class, name = "DELETE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderStatusRequest.class, name = "UPDATE_ORDER_STATUS")
})
public interface KafkaEvent extends EventSource {
}