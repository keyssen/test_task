package com.task.mediasoft.order.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveOrderDTO {

    @NotNull(message = "Customer ID is required")
    private final Long customerId;

    @NotBlank(message = "Article is required")
    @Size(min = 3, max = 255)
    private final String deliveryAddress;
}
