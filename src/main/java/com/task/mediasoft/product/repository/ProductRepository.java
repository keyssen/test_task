package com.task.mediasoft.product.repository;

import com.task.mediasoft.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product " +
            "p WHERE lower(p.name) LIKE lower(concat('%', :search, '%')) " +
            "OR lower(p.description) LIKE lower(concat('%', :search, '%')) " +
            "OR lower(p.category) LIKE lower(concat('%', :search, '%')) " +
            "OR lower(p.article) = lower(:search)")
    Page<Product> findProducts(Pageable pageable, @Param("search") String search);

    Optional<Product> findByArticleEquals(String Article);
}