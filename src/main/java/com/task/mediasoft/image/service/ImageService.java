package com.task.mediasoft.image.service;

import com.task.mediasoft.image.ImageEntity;
import com.task.mediasoft.product.model.Product;

import java.util.List;
import java.util.UUID;

public interface ImageService {
    List<ImageEntity> getImagesByProductId(UUID productId);

    ImageEntity createImage(Product product);
}
