package com.task.mediasoft.order.model.dto;

import com.task.mediasoft.order.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) для изменения статуса заказа.
 */
@Data
public class ChangeStatusDTO {

    /**
     * Новый статус заказа.
     */
    @NotNull(message = "Status is required")
    private OrderStatus status;
    
    private LocalDateTime deliveryDateTime;
}
