package com.task.mediasoft.product.model.dto;

import com.task.mediasoft.product.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO класс для сохранения информации о продукте.
 * {@link Product}
 * Содержит аннотации для валидации входных данных.
 */
@Getter
@Setter
@NoArgsConstructor
public class SaveProductDTO {

    /**
     * Артикул продукта.
     * Не может быть пустым и должен находиться в диапазоне от 3 до 255 символов.
     */
    @NotBlank(message = "Article is required")
    @Size(min = 3, max = 255)
    private String article;

    /**
     * Наименование продукта.
     * Не может быть пустым и должно содержать от 3 до 255 символов.
     */
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255)
    private String name;

    /**
     * Описание продукта.
     * Не может быть пустым и должно содержать от 3 до 512 символов.
     */
    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 512)
    private String description;

    /**
     * Категория продукта.
     * Не может быть пустой.
     */
    @NotNull(message = "Category is required")
    @Size(min = 3, max = 255)
    private String category;

    /**
     * Цена продукта.
     * Не может быть пустой и должна быть положительной.
     */
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private Double price;

    /**
     * Количество продукта.
     * Не может быть пустым и должно быть положительным или нулевым.
     */
    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be a positive number")
    private Integer quantity;
}
