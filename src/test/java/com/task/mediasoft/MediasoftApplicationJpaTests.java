package com.task.mediasoft;

import com.task.mediasoft.product.model.CategoryType;
import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.product.model.dto.SaveProductDTO;
import com.task.mediasoft.product.repository.ProductRepository;
import com.task.mediasoft.product.service.ProductService;
import com.task.mediasoft.product.service.searchCriteria.Criterial.BigDecimalSearchCriterial;
import com.task.mediasoft.product.service.searchCriteria.Criterial.LocalDateTimeSearchCriterial;
import com.task.mediasoft.product.service.searchCriteria.Criterial.SearchCriterial;
import com.task.mediasoft.product.service.searchCriteria.Criterial.StringSearchCriterial;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестовый класс для проверки функциональности работы приложения.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class MediasoftApplicationJpaTests {

    @Autowired
    ProductRepository productRepository;
    ProductService productService;


    /**
     * Создает и возвращает объект SaveProductDTO для использования в тестах.
     *
     * @param number   порядковый номер товара
     * @param quantity количество товара
     * @return объект SaveProductDTO
     */
    private SaveProductDTO createProductDto(Integer number, Long quantity) {
        SaveProductDTO saveProductDTO = new SaveProductDTO();
        saveProductDTO.setName("Product" + number);
        saveProductDTO.setCategory(CategoryType.BOOKS);
        saveProductDTO.setDescription("Description" + number);
        saveProductDTO.setQuantity(quantity);
        saveProductDTO.setPrice(BigDecimal.valueOf(100.1));
        saveProductDTO.setArticle("Article-" + number);
        return saveProductDTO;
    }


    /**
     * Инициализация данных перед запуском всех тестов.
     */
    @BeforeAll
    void init() {
        productService = new ProductService(productRepository);
        productService.createProduct(createProductDto(1, 5L));
        productService.createProduct(createProductDto(2, 10L));
        productService.createProduct(createProductDto(3, 15L));
        productService.createProduct(createProductDto(4, 20L));
        productService.createProduct(createProductDto(5, 25L));
    }


    /**
     * Тестирование критериев запросов Criteria API для поиска товаров.
     */
    @Test
    void testCriteriaApi() {
        BigDecimal quantity = BigDecimal.valueOf(15);
        String article = "Article";
        String name = "5";

        BigDecimalSearchCriterial quantityFilter = new BigDecimalSearchCriterial();
        quantityFilter.setField("quantity");
        quantityFilter.setOperation(">=");
        quantityFilter.setValue(quantity);

        StringSearchCriterial articleFilter = new StringSearchCriterial();
        articleFilter.setField("article");
        articleFilter.setOperation(">=");
        articleFilter.setValue(article);

        StringSearchCriterial nameFilter = new StringSearchCriterial();
        nameFilter.setField("name");
        nameFilter.setOperation("<=");
        nameFilter.setValue(name);

        LocalDateTimeSearchCriterial localDateTimeFilter = new LocalDateTimeSearchCriterial();
        localDateTimeFilter.setField("lastQuantityChangeDate");
        localDateTimeFilter.setOperation("=");
        localDateTimeFilter.setValue(LocalDateTime.now());

        List<SearchCriterial<?>> filters = new ArrayList<>();
        filters.add(quantityFilter);
        filters.add(articleFilter);
        filters.add(nameFilter);
        filters.add(localDateTimeFilter);

        Page<Product> productsPage = productService.searchProductsCriteriaApi(PageRequest.of(0, 5), filters);
        Product product = productsPage.getContent().get(0);

        assertEquals(1, productsPage.getContent().size(), "Ожидается 1 товар в результате фильтрации");
        assertTrue(BigDecimal.valueOf(product.getQuantity()).compareTo(quantity) >= 0,
                String.format("Количество таких товаров должно быть больше %s", quantity));
        assertTrue(product.getArticle().startsWith(article), "Ожидается товар с арткулом, начинающимся на 'Article'");
        assertTrue(product.getName().endsWith(name), "Ожидается товар с арткулом, заканчивающимся на '5'");
        assertTrue(product.getLastQuantityChangeDate().toLocalDate().equals(LocalDateTime.now().toLocalDate()), "Ожидается что времяя одинаковое");
    }
}