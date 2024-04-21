package com.task.mediasoft.product.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProductFilterDto {
    private UUID id;

    /**
     * Артикул продукта.
     */
    private String article;

    /**
     * Наименование продукта.
     */
    private String name;

//    /**
//     * Описание продукта.
//     */
//    private String description;
//
//    /**
//     * Категория продукта.
//     */
//    private CategoryType category;
//
    /**
     * Цена продукта.
     */
    private BigDecimal price;
//
//    /**
//     * Количество продукта.
//     */
//    private Long quantity;
//
//    /**
//     * Дата последнего изменения количества продукта.
//     */
//    private LocalDateTime lastQuantityChangeDate;
//
//    /**
//     * Дата создания продукта.
//     */
//    private LocalDate creationDate;
//
    /**
     * Номер страницы.
     */
    @NotNull
    private Integer page;

    /**
     * Количество продуктов на странице.
     */
    @NotNull
    private Integer size;
}
