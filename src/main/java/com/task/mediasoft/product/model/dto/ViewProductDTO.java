package com.task.mediasoft.product.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.mediasoft.product.model.CategoryType;
import com.task.mediasoft.product.model.Product;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) для отображения информации о продукте.
 */
@Getter
public class ViewProductDTO {

    /**
     * Идентификатор продукта.
     */
    private final UUID id;

    /**
     * Артикул продукта.
     */
    private final String article;

    /**
     * Наименование продукта.
     */
    private final String name;

    /**
     * Описание продукта.
     */
    private final String description;

    /**
     * Категория продукта.
     */
    private final CategoryType category;

    /**
     * Цена продукта.
     */
    private final BigDecimal price;

    /**
     * Количество продукта.
     */
    private final Long quantity;

    /**
     * Дата последнего изменения количества продукта.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime lastQuantityChangeDate;

    /**
     * Дата создания продукта.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate creationDate;

    /**
     * Доступность продукта.
     */
    private final Boolean isAvailable;

    /**
     * Конструктор для создания объекта ViewProductDTO.
     *
     * @param product Объект Product, на основе которого создается ViewProductDTO.
     */
    public ViewProductDTO(Product product) {
        this.id = product.getId();
        this.article = product.getArticle();
        this.name = product.getName();
        this.description = product.getDescription();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.lastQuantityChangeDate = product.getLastQuantityChangeDate();
        this.creationDate = product.getCreationDate();
        this.isAvailable = product.getIsAvailable();
    }
}