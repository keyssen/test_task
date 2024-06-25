package com.task.mediasoft.image.repository;

import com.task.mediasoft.customer.model.Customer;
import com.task.mediasoft.image.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link Customer} в базе данных.
 */
public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {

    @Query("SELECT i FROM ImageEntity i WHERE i.product.id = :productId")
    Optional<List<ImageEntity>> findImages(@Param("productId") UUID productId);

}
