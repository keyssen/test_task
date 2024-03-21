package com.task.mediasoft.product.controller;

import com.task.mediasoft.product.model.dto.SaveProductDTO;
import com.task.mediasoft.product.model.dto.ViewProductDTO;
import com.task.mediasoft.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Контроллер для управления продуктами.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Получает список всех продуктов с пагинацией и поиском.
     * @param page Номер страницы.
     * @param size Размер страницы.
     * @param search Строка для поиска по продуктам (опционально).
     * @return Ответ с данными о продуктах.
     */
    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllProducts(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "5") int size,
                                                             @RequestParam(required = false) String search) {
        try{
            Page<ViewProductDTO> products = productService.getAllProducts(page,size,search).map(ViewProductDTO::new);
            Map<String, Object> response = new HashMap<>();
            response.put("products", products.get().toList());
            response.put("totalItems", products.getTotalElements());
            response.put("totalPages", products.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Получает продукт по его артикулу.
     * @param article Артикул продукта.
     * @return Данные о продукте.
     */
    @GetMapping("/getProductByArticle")
    public ViewProductDTO getProductByArticle(@RequestParam(value = "article") String article) {
        return new ViewProductDTO(productService.getProductByArticle(article));
    }

    /**
     * Получает продукт по его идентификатору.
     * @param id Идентификатор продукта.
     * @return Данные о продукте.
     */
    @GetMapping("/{id}")
    public ViewProductDTO getProductById(@PathVariable UUID id) {
        return new ViewProductDTO(productService.getProductById(id));
    }


    /**
     * Создает новый продукт.
     * @param productDTO Данные для создания продукта.
     * @return Данные о созданном продукте.
     */
    @PostMapping
    public ViewProductDTO createProduct(@Valid @RequestBody SaveProductDTO productDTO) {
        return new ViewProductDTO(productService.createProduct(productDTO));
    }

    /**
     * Обновляет данные существующего продукта.
     * @param id Идентификатор продукта.
     * @param productDTO Новые данные продукта.
     * @return Обновленные данные о продукте.
     */
    @PutMapping("/{id}")
    public ViewProductDTO updateProduct(@PathVariable UUID id, @Valid @RequestBody SaveProductDTO productDTO) {
        return new ViewProductDTO(productService.updateProduct(id, productDTO));
    }

    /**
     * Удаляет продукт по его идентификатору.
     * @param id Идентификатор продукта.
     * @return Ответ об успешном удалении.
     */
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}