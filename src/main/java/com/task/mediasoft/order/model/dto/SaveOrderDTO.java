package com.task.mediasoft.order.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class SaveOrderDTO {
    @NotBlank(message = "Delivery address is required")
    @Size(min = 3, max = 255)
    private final String deliveryAddress;

    @NotEmpty(message = "Products is required")
    private final List<SaveOrderProductDTO> products;
}
