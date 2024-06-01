package com.task.mediasoft.kafka.request;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
public interface HttpEvent extends EventSource{
}
