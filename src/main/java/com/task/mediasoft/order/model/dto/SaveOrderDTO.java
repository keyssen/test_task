package com.task.mediasoft.order.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) для сохранения информации о заказе.
 */
@Data
public class SaveOrderDTO {

    /**
     * Адрес доставки заказа.
     */
    @NotBlank(message = "Delivery address is required")
    @Size(min = 3, max = 255)
    private final String deliveryAddress;

    /**
     * Список продуктов, входящих в заказ.
     */
    @NotEmpty(message = "Products is required")
    private final List<SaveOrderProductDTO> products;
}