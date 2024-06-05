package com.task.mediasoft.image.service;


import com.task.mediasoft.configuration.properties.S3Properties;
import com.task.mediasoft.image.Image;
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
    private final S3Properties s3Properties;

    @Transactional(readOnly = true)
    public List<Image> getImagesByProductId(UUID productId) {
        return imageRepository.findImages(productId)
                .orElseThrow(() -> new RuntimeException("Not found images bu productId "));
    }

    @Transactional
    public Image createImage(Product product) {
        Image image = new Image();
        image.setProduct(product);
        return imageRepository.save(image);
    }
}
