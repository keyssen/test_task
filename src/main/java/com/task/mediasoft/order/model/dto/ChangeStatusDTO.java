package com.task.mediasoft.order.model.dto;

import com.task.mediasoft.order.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeStatusDTO {
    @NotNull(message = "Status is required")
    private OrderStatus status;
}
