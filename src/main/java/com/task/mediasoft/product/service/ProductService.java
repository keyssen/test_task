package com.task.mediasoft.product.service;

import com.task.mediasoft.product.exception.ProductNotFoundExceptionByArticle;
import com.task.mediasoft.product.exception.ProductNotFoundExceptionById;
import com.task.mediasoft.product.exception.ProductWithArticleAlreadyExistsException;
import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.product.model.dto.SaveProductDTO;
import com.task.mediasoft.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Класс реализующий бизнес логику для работы с продуктами
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Получение продуктов с возможностью поиска по строке.
     *
     * @param page   Номер страницы.
     * @param size   Количество элементов на странице.
     * @param search Строка поиска по имени продукта.
     * @return Страница продуктов.
     */
    @Transactional(readOnly = true)
    public Page<Product> getAllProducts(int page, int size, String search) {
        if (search != null && !search.isEmpty()) {
            return productRepository.findProducts(PageRequest.of(page - 1, size, Sort.by("id")), search);
        }
        return productRepository.findAll(PageRequest.of(page - 1, size, Sort.by("id")));
    }

    /**
     * Получение продукта по идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return Найденный продукт.
     * @throws ProductNotFoundExceptionById если продукт не найден.
     */
    @Transactional(readOnly = true)
    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundExceptionById(id));
    }

    /**
     * Получение продукта по артикулу.
     *
     * @param article Артикул продукта.
     * @return Найденный продукт.
     * @throws ProductNotFoundExceptionByArticle если продукт не найден.
     */
    @Transactional(readOnly = true)
    public Product getProductByArticle(String article) {
        return productRepository.findByArticleEquals(article).orElseThrow(() -> new ProductNotFoundExceptionByArticle(article));
    }

    /**
     * Создание нового продукта.
     *
     * @param createProductDTO DTO для создания продукта.
     * @return Созданный продукт.
     */
    @Transactional
    public Product createProduct(SaveProductDTO createProductDTO) {
        if (productRepository.existsByArticle(createProductDTO.getArticle())) {
            throw new ProductWithArticleAlreadyExistsException(createProductDTO.getArticle());
        }
        Product product = new Product(createProductDTO);
        product.setCreationDate(LocalDateTime.now());
        product.setLastQuantityChangeDate(LocalDateTime.now());
        return productRepository.save(product);
    }

    /**
     * Обновление информации о продукте.
     *
     * @param id                Идентификатор продукта.
     * @param updateProducttDTO DTO с обновленными данными продукта.
     * @return Обновленный продукт.
     */
    @Transactional
    public Product updateProduct(UUID id, SaveProductDTO updateProducttDTO) {
        Product currentProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundExceptionById(id));
        if (productRepository.existsByArticle(updateProducttDTO.getArticle()) && !Objects.equals(currentProduct.getArticle(), updateProducttDTO.getArticle())) {
            throw new ProductWithArticleAlreadyExistsException(currentProduct.getArticle());
        }
        if (!Objects.equals(currentProduct.getQuantity(), updateProducttDTO.getQuantity())) {
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

    /**
     * Удаление продукта по идентификатору.
     *
     * @param id Идентификатор продукта.
     */
    @Transactional
    public void deleteProduct(UUID id) {
        Product currentProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundExceptionById(id));
        productRepository.delete(currentProduct);
    }

    /**
     * Удаление всех продуктов.
     */
    @Transactional
    public void deleteAllProduct() {
        productRepository.deleteAll();
    }
}
