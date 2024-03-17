package com.task.mediasoft.product.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SaveProductDTO {

    @NotBlank(message = "Article is required")
    @Size(min = 3, max = 255)
    private String article;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255)
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 512)
    private String description;

    @NotNull(message = "Category is required")
    private String category;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private Double price;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be a positive number")
    private Integer quantity;
}
