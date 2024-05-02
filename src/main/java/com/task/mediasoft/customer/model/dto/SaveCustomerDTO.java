package com.task.mediasoft.customer.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveCustomerDTO {

    @NotBlank(message = "Login is required")
    @Size(min = 3, max = 255)
    private final String login;

    @NotBlank(message = "Email is required")
    @Size(min = 3, max = 255)
    private final String email;
}
