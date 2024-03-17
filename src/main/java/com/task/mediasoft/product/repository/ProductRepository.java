package com.task.mediasoft.product.repository;

import com.task.mediasoft.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findByNameContainingOrDescriptionContainingOrArticleEquals(Pageable pageable, String name, String description, String article);
}