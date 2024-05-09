package com.task.mediasoft.product.service;

import com.task.mediasoft.product.exception.ProductNotFoundExceptionByArticle;
import com.task.mediasoft.product.exception.ProductNotFoundExceptionById;
import com.task.mediasoft.product.exception.ProductWithArticleAlreadyExistsException;
import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.product.model.dto.SaveProductDTO;
import com.task.mediasoft.product.repository.ProductRepository;
import com.task.mediasoft.session.CurrencyProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Класс реализующий бизнес логику для работы с продуктами
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CurrencyProvider currencyProvider;
    private final ExchangeRateProvider exchangeRateProvider;

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
     * Получение продукта по идентификатору и изменение его цены в соотвествии с валютой.
     *
     * @param id Идентификатор продукта.
     * @return Найденный продукт.
     * @throws ProductNotFoundExceptionById если продукт не найден.
     */
    @Transactional(readOnly = true)
    public Product getProductById(UUID id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundExceptionById(id));
        product.setPrice(getNewPrice(product.getPrice(), exchangeRateProvider.getExchangeRate(currencyProvider.getCurrency())));
        return product;
    }

    /**
     * Получение продукта по артикулу и изменение его цены в соотвествии с валютой.
     *
     * @param article Артикул продукта.
     * @return Найденный продукт.
     * @throws ProductNotFoundExceptionByArticle если продукт не найден.
     */
    @Transactional(readOnly = true)
    public Product getProductByArticle(String article) {
        final Product product = productRepository.findByArticleEquals(article)
                .orElseThrow(() -> new ProductNotFoundExceptionByArticle(article));
        product.setPrice(getNewPrice(product.getPrice(), exchangeRateProvider.getExchangeRate(currencyProvider.getCurrency())));
        return product;
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

    /**
     * Вычисляет новую цену, разделив заданную цену на предоставленный обменный курс валюты,
     * округленный до двух десятичных знаков с использованием режима округления {@link RoundingMode#HALF_UP}.
     * <p>
     * Этот метод обычно используется для корректировки цен в зависимости от колебаний валюты.
     * </p>
     *
     * @param price    Исходная цена, которая должна быть скорректирована.
     * @param currency Обменный курс валюты, используемый для корректировки.
     * @return Новая цена, скорректированная согласно предоставленному обменному курсу валюты
     * и округленная до двух десятичных знаков.
     */
    public BigDecimal getNewPrice(BigDecimal price, BigDecimal currency) {
        return price.divide(currency, 2, RoundingMode.HALF_UP);
    }
}
