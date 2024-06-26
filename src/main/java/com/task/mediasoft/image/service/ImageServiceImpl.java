package com.task.mediasoft.image.service;


import com.task.mediasoft.image.ImageEntity;
import com.task.mediasoft.image.repository.ImageRepository;
import com.task.mediasoft.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с клиентами.
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<ImageEntity> getImagesByProductId(UUID productId) {
        return imageRepository.findImages(productId)
                .orElseThrow(() -> new RuntimeException("Not found images bu productId "));
    }

    @Transactional
    public ImageEntity createImage(Product product) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setProduct(product);
        return imageRepository.save(imageEntity);
    }
}
