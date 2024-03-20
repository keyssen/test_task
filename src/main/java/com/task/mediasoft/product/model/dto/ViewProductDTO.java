package com.task.mediasoft.product.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.mediasoft.product.model.Product;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ViewProductDTO {
    private final UUID id;

    private final String article;

    private final String name;

    private final String description;

    private final String category;

    private final Double price;

    private final Integer quantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime lastQuantityChangeDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime creationDate;

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