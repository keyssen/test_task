package com.task.mediasoft.product.repository;

import com.task.mediasoft.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс репозитория для сущности Product.
 * {@link Product}.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    /**
     * Получает продукты, соответствующие заданным критериям поиска.
     *
     * @param pageable информация о пагинации
     * @param search   строка поиска для фильтрации продуктов по имени, описанию, категории или артикулу
     * @return страница продуктов, соответствующих критериям поиска
     */
    @Query("SELECT p FROM Product " +
            "p WHERE lower(p.name) LIKE lower(concat('%', :search, '%')) " +
            "OR lower(p.description) LIKE lower(concat('%', :search, '%')) " +
            "OR lower(p.category) LIKE lower(concat('%', :search, '%')) " +
            "OR lower(p.article) = lower(:search)")
    Page<Product> findProducts(Pageable pageable, @Param("search") String search);

    /**
     * Получает объект Product с указанным артикулом (article).
     *
     * @param article артикул, который необходимо найти
     * @return Optional сущности Product, если найдено, в противном случае пусто
     */
    Optional<Product> findByArticleEquals(String article);

    /**
     * Проверка существования продукта по артикулу.
     *
     * @param article Артикул продукта.
     * @return True, если продукт с указанным артикулом существует, иначе false.
     */
    boolean existsByArticle(String article);
}