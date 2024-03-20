package com.task.mediasoft.product.service;

import com.task.mediasoft.product.exception.ProductNotFoundExceptionByArticle;
import com.task.mediasoft.product.exception.ProductNotFoundExceptionById;
import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.product.model.dto.SaveProductDTO;
import com.task.mediasoft.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService{
    private final ProductRepository productRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Transactional(readOnly = true)
    public Page<Product> getAllProducts(int page, int size, String search) {
        if (search != null && !search.isEmpty()){
            return productRepository.findProducts(PageRequest.of(page - 1, size, Sort.by("id")), search);
        }
        return productRepository.findAll(PageRequest.of(page - 1, size, Sort.by("id")));
    }

    @Transactional(readOnly = true)
    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundExceptionById(id));
    }

    @Transactional(readOnly = true)
    public Product getProductByArticle(String article) {
        return productRepository.findByArticleEquals(article).orElseThrow(() -> new ProductNotFoundExceptionByArticle(article));
    }

    @Transactional
    public Product createProduct(SaveProductDTO createProductDTO) {
        Product product = new Product(createProductDTO.getArticle(), createProductDTO.getName(), createProductDTO.getDescription(), createProductDTO.getCategory(), createProductDTO.getPrice(), createProductDTO.getQuantity());
        product.setCreationDate(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(UUID id, SaveProductDTO updateProducttDTO) {
        Product currentProduct = getProductById(id);

        if (currentProduct.getQuantity() != updateProducttDTO.getQuantity()){
            currentProduct.setQuantity(updateProducttDTO.getQuantity());
            currentProduct.setLastQuantityChangeDate(LocalDateTime.now());
        }

        currentProduct.setName(updateProducttDTO.getName());
        currentProduct.setArticle(updateProducttDTO.getArticle());
        currentProduct.setDescription(updateProducttDTO.getDescription());
        currentProduct.setCategory(updateProducttDTO.getCategory());
        currentProduct.setPrice(updateProducttDTO.getPrice());
        currentProduct.setQuantity(updateProducttDTO.getQuantity());

        return productRepository.save(currentProduct);
    }

    @Transactional
    public void deleteProduct(UUID id) {
        Product currentProduct = getProductById(id);
        productRepository.delete(currentProduct);
    }

    @Transactional
    public void deleteAllProduct() {
        productRepository.deleteAll();
    }
}
