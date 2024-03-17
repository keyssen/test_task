package com.task.mediasoft.product.service;

import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.product.model.dto.SaveProductDTO;
import com.task.mediasoft.product.service.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    Page<Product> getAllProducts(int page, int size, String search);

    Product getProductById(UUID id) throws ProductNotFoundException;

    Product createProduct(SaveProductDTO productDTO);

    Product updateProduct(UUID id, SaveProductDTO productDTO) throws ProductNotFoundException;

    void deleteProduct(UUID id) throws ProductNotFoundException;
}