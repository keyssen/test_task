package com.task.mediasoft.product.controller;

import com.task.mediasoft.product.controller.model.ProductPaginationModel;
import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.product.model.dto.SaveProductDTO;
import com.task.mediasoft.product.model.dto.ViewProductDTO;
import com.task.mediasoft.product.service.ExchangeRateProvider;
import com.task.mediasoft.product.service.ProductService;
import com.task.mediasoft.session.CurrencyEnum;
import com.task.mediasoft.session.CurrencyProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Контроллер для управления продуктами.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ExchangeRateProvider exchangeRateProvider;
    private final CurrencyProvider currencyProvider;

    /**
     * Получает список всех продуктов с пагинацией и поиском.
     *
     * @param page   Номер страницы.
     * @param size   Размер страницы.
     * @param search Строка для поиска по продуктам (опционально).
     * @return Ответ с данными о продуктах.
     */
    @GetMapping
    public ResponseEntity<ProductPaginationModel> getAllProducts(@RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "5") int size,
                                                                 @RequestParam(required = false) String search) {
        Page<ViewProductDTO> products = productService.getAllProducts(page, size, search).map(ViewProductDTO::new);
        List<ViewProductDTO> viewProducts = new ArrayList<>();
        CurrencyEnum currency = currencyProvider.getCurrency();
        BigDecimal currencyPrice = exchangeRateProvider.getExchangeRate(currency);
        for (Product product : productService.getAllProducts(page, size, search)) {
            ViewProductDTO viewProductDTO = new ViewProductDTO(product);
            viewProductDTO.setPrice(productService.getNewPrice(product.getPrice(), currencyPrice));
            viewProductDTO.setCurrency(currency);
            viewProducts.add(viewProductDTO);
        }
        ProductPaginationModel response = new ProductPaginationModel(viewProducts, products.getTotalElements(), products.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Получает продукт по его артикулу и меняет валюту.
     *
     * @param article Артикул продукта.
     * @return Данные о продукте.
     */
    @GetMapping("/getProductByArticle/{article}")
    public ViewProductDTO getProductByArticle(@PathVariable String article) {
        ViewProductDTO viewProductDTO = new ViewProductDTO(productService.getProductByArticle(article));
        viewProductDTO.setCurrency(currencyProvider.getCurrency());
        return viewProductDTO;
    }

    /**
     * Получает продукт по его идентификатору и меняет валюту.
     *
     * @param id Идентификатор продукта.
     * @return Данные о продукте.
     */
    @GetMapping("/{id}")
    public ViewProductDTO getProductById(@PathVariable UUID id) {
        ViewProductDTO viewProductDTO = new ViewProductDTO(productService.getProductById(id));
        viewProductDTO.setCurrency(currencyProvider.getCurrency());
        return viewProductDTO;
    }


    /**
     * Создает новый продукт.
     *
     * @param productDTO Данные для создания продукта.
     * @return Данные о созданном продукте.
     */
    @PostMapping
    public ViewProductDTO createProduct(@Valid @RequestBody SaveProductDTO productDTO) {
        return new ViewProductDTO(productService.createProduct(productDTO));
    }

    /**
     * Обновляет данные существующего продукта.
     *
     * @param id         Идентификатор продукта.
     * @param productDTO Новые данные продукта.
     * @return Обновленные данные о продукте.
     */
    @PutMapping("/{id}")
    public ViewProductDTO updateProduct(@PathVariable UUID id, @Valid @RequestBody SaveProductDTO productDTO) {
        return new ViewProductDTO(productService.updateProduct(id, productDTO));
    }

    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return Ответ об успешном удалении.
     */
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}