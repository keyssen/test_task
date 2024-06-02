package com.task.mediasoft.image.repository;

import com.task.mediasoft.customer.model.Customer;
import com.task.mediasoft.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link Customer} в базе данных.
 */
public interface ImageRepository extends JpaRepository<Image, UUID> {

    @Query("SELECT i FROM Image i WHERE i.product.id = :productId")
    Optional<List<Image>> findImages(@Param("productId") UUID productId);
    
}
