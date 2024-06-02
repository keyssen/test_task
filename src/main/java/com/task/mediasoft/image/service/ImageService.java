package com.task.mediasoft.image.service;

import com.task.mediasoft.image.Image;
import com.task.mediasoft.product.model.Product;

import java.util.List;
import java.util.UUID;

public interface ImageService {
    List<Image> getImagesByProductId(UUID productId);

    void createImage(Product product, String fileName);
}
