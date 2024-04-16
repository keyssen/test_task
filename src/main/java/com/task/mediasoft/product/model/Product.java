package com.task.mediasoft.product.model;

import com.task.mediasoft.product.model.dto.SaveProductDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность, представляющая продукт.
 */
@Entity
@Data
@NoArgsConstructor
public class Product {
    /**
     * Уникальный идентификатор продукта.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Артикул продукта.
     */
    @NonNull
    @Column(nullable = false, unique = true)
    private String article;

    /**
     * Наименование продукта.
     */
    @NonNull
    @Column(nullable = false)
    private String name;

    /**
     * Описание продукта.
     */
    @NonNull
    @Column(nullable = false)
    private String description;

    /**
     * Категория продукта.
     */
    @NonNull
    @Column(nullable = false)
    private String category;

    /**
     * Цена продукта.
     */
    @NonNull
    @Column(nullable = false, columnDefinition = "numeric(10,2)")
    private Double price;

    /**
     * Количество продукта.
     */
    @NonNull
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Дата последнего изменения количества продукта.
     */
    @NonNull
    @Column(name = "last_quantity_change_date", nullable = false)
    private LocalDateTime lastQuantityChangeDate;

    /**
     * Дата создания продукта.
     */
    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDateTime creationDate;

    /**
     * Конструктор для создания нового продукта  на основе объекта {@link SaveProductDTO}.
     *
     * @param saveProductDTO Объект {@link SaveProductDTO}, на основе которого создается продукт.
     */
    public Product(SaveProductDTO saveProductDTO) {
        this.article = saveProductDTO.getArticle();
        this.name = saveProductDTO.getName();
        this.description = saveProductDTO.getDescription();
        this.category = saveProductDTO.getCategory();
        this.price = saveProductDTO.getPrice();
        this.quantity = saveProductDTO.getQuantity();
    }
}