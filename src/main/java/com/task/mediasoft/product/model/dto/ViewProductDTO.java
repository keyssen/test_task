package com.task.mediasoft.product.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.mediasoft.product.model.CategoryType;
import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.session.CurrencyEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) для отображения информации о продукте.
 */
@Getter
@Setter
public class ViewProductDTO {

    /**
     * Идентификатор продукта.
     */
    private UUID id;

    /**
     * Артикул продукта.
     */
    private String article;

    /**
     * Наименование продукта.
     */
    private String name;

    /**
     * Описание продукта.
     */
    private String description;

    /**
     * Категория продукта.
     */
    private CategoryType category;

    /**
     * Цена продукта.
     */
    private BigDecimal price;

    /**
     * Количество продукта.
     */
    private Long quantity;

    /**
     * Дата последнего изменения количества продукта.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastQuantityChangeDate;

    /**
     * Дата создания продукта.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    /**
     * Валюта продукта.
     */
    private CurrencyEnum currency;

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
        this.currency = CurrencyEnum.RUB;
    }
}