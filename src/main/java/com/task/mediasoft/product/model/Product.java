package com.task.mediasoft.product.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(indexes = @Index(name = "product_article", columnList = "article", unique = true))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    @Column(nullable = false)
    private String article;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String description;

    @NonNull
    @Column(nullable = false)
    private String category;

    @NonNull
    @Column(nullable = false)
    private Double price;

    @NonNull
    @Column(nullable = false)
    private Integer quantity;

    private LocalDateTime lastQuantityChangeDate;

    @Column(updatable = false)
    private LocalDateTime creationDate;

    public Product(String article, String name, String description, String category, Double price, Integer quantity) {
        this.article = article;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }
}