package com.task.mediasoft.product.model;

import com.task.mediasoft.orderProduct.model.OrderProduct;
import com.task.mediasoft.product.model.dto.SaveProductDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Сущность, представляющая продукт.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@Builder
public class Product {
    /**
     * Уникальный идентификатор продукта.
     */
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private UUID id;

    /**
     * Артикул продукта.
     */
    @NonNull
    @Column(name = "article", nullable = false, updatable = false, unique = true)
    private String article;

    /**
     * Наименование продукта.
     */
    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Описание продукта.
     */
    @NonNull
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Категория продукта.
     */
    @NonNull
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    /**
     * Цена продукта.
     */
    @NonNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /**
     * Количество продукта.
     */
    @NonNull
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    /**
     * Дата последнего изменения количества продукта.
     */
    @NonNull
    @UpdateTimestamp
    @Column(name = "last_quantity_change_date", nullable = false)
    private LocalDateTime lastQuantityChangeDate;

    /**
     * Дата создания продукта.
     */
    @NonNull
    @CreationTimestamp
    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDate creationDate;

    /**
     * Доступность продукта.
     */
    @NonNull
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;


    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProducts;

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
        this.isAvailable = saveProductDTO.getIsAvailable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) && Objects.equals(getArticle(), product.getArticle()) &&
                Objects.equals(getName(), product.getName()) && Objects.equals(getDescription(), product.getDescription()) &&
                getCategory() == product.getCategory() && Objects.equals(getPrice(), product.getPrice()) &&
                Objects.equals(getQuantity(), product.getQuantity()) && Objects.equals(getLastQuantityChangeDate(), product.getLastQuantityChangeDate()) &&
                Objects.equals(getCreationDate(), product.getCreationDate()) && Objects.equals(getIsAvailable(), product.getIsAvailable());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getArticle(), getName(), getDescription(), getCategory(), getPrice(), getQuantity(), getLastQuantityChangeDate(), getCreationDate(), getIsAvailable());
    }

}