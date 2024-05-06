package com.task.mediasoft.customer.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object (DTO) для передачи данных для создания нового клиента.
 */
@Data
public class SaveCustomerDTO {

    /**
     * Логин клиента.
     */
    @NotBlank(message = "Login is required")
    @Size(min = 3, max = 255)
    private final String login;

    /**
     * Адрес электронной почты клиента.
     */
    @NotBlank(message = "Email is required")
    @Size(min = 3, max = 255)
    private final String email;

    /**
     * Флаг, указывающий, активен ли клиент.
     */
    @NotNull(message = "Is active is required")
    private final Boolean isActive;
}
