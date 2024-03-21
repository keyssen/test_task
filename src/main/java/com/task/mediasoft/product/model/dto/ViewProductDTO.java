package com.task.mediasoft.product.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.mediasoft.product.model.Product;
import lombok.Getter;

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
    private final String category;

    /**
     * Цена продукта.
     */
    private final Double price;

    /**
     * Количество продукта.
     */
    private final Integer quantity;

    /**
     * Дата последнего изменения количества продукта.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime lastQuantityChangeDate;

    /**
     * Дата создания продукта.
     */
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime creationDate;

    /**
     * Конструктор для создания объекта ViewProductDTO.
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
    }
}