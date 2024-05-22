package com.task.mediasoft.order.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) для сохранения информации о продукте заказа.
 */
@Data
public class SaveOrderProductDTO {

    /**
     * Идентификатор продукта.
     */
    @NotNull(message = "Id address is required")
    private final UUID id;

    /**
     * Количество продукта.
     */
    @NotNull(message = "Quantity address is required")
    private final Long quantity;
}