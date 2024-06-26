package com.task.mediasoft.order.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) для сохранения информации о продукте заказа.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveOrderProductDTO {

    /**
     * Идентификатор продукта.
     */
    @NotNull(message = "Id address is required")
    private UUID id;

    /**
     * Количество продукта.
     */
    @NotNull(message = "Quantity address is required")
    private Long quantity;
}