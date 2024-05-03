package com.task.mediasoft.order.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SaveOrderProductDTO {

    @NotNull(message = "Id address is required")
    private final UUID id;

    @NotNull(message = "Quantity address is required")
    private final Long quantity;
}
