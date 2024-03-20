package com.task.mediasoft;

import com.task.mediasoft.product.exception.ProductNotFoundExceptionByArticle;
import com.task.mediasoft.product.exception.ProductNotFoundExceptionById;
import com.task.mediasoft.product.model.Product;
import com.task.mediasoft.product.model.dto.SaveProductDTO;
import com.task.mediasoft.product.service.ProductService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MediasoftApplicationTests {

	private final Logger log = LoggerFactory.getLogger(MediasoftApplicationTests.class);

	@Autowired
	private ProductService productService;

	private void productEquals(SaveProductDTO expected, Product actual) {
		Assertions.assertEquals(expected.getArticle(), actual.getArticle());
		Assertions.assertEquals(expected.getName(), actual.getName());
		Assertions.assertEquals(expected.getDescription(), actual.getDescription());
		Assertions.assertEquals(expected.getCategory(), actual.getCategory());
		Assertions.assertEquals(expected.getPrice(), actual.getPrice());
		Assertions.assertEquals(expected.getQuantity(), actual.getQuantity());
	}

	private void localDateTimeEquals(LocalDateTime expected, LocalDateTime actual) {
		Assertions.assertEquals(expected.withNano(0), actual.withNano(0));
	}

	private SaveProductDTO createProductDto(Integer number) {
		SaveProductDTO saveProductDTO = new SaveProductDTO();
		saveProductDTO.setName("Product" + number);
		saveProductDTO.setCategory("Category" + number);
		saveProductDTO.setDescription("Description" + number);
		saveProductDTO.setQuantity(10);
		saveProductDTO.setPrice(10.1);
		saveProductDTO.setArticle("Product-" + number);
		return saveProductDTO;
	}

	@BeforeAll
	void init() {
		productService.deleteAllProduct();
	}

	@AfterEach
	void down() {
		productService.deleteAllProduct();
	}

	@Test
	void searchProductsByArticle() {
		final Product product1 = productService.createProduct(createProductDto(1));
		final Product product2 = productService.createProduct(createProductDto(2));
		log.info("product1: " + product1.toString());
		log.info("product2: " + product2.toString());
		Assertions.assertEquals(productService.getAllProducts(1, 5, "Product-1").get().count(), 1);
	}

	@Test
	void searchProducts() {
		final Product product1 = productService.createProduct(createProductDto(1));
		final Product product2 = productService.createProduct(createProductDto(2));
		log.info("product1: " + product1.toString());
		log.info("product2: " + product2.toString());
		Assertions.assertEquals(productService.getAllProducts(1, 5, "Product1").get().count(), 1);
		Assertions.assertEquals(productService.getAllProducts(1, 5, null).getTotalElements(), 2);
		Assertions.assertEquals(productService.getAllProducts(1, 5, "product1").get().count(), 1);
		Assertions.assertEquals(productService.getAllProducts(1, 5, "product").get().count(), 2);
	}

	@Test
	void searchAllProducts() {
		final Product product1 = productService.createProduct(createProductDto(1));
		final Product product2 = productService.createProduct(createProductDto(2));
		log.info("product1: " + product1.toString());
		log.info("product2: " + product2.toString());
		Assertions.assertEquals(productService.getAllProducts(1, 5, null).get().count(), 2);
	}

	@Test
	void findProductById() {
		SaveProductDTO saveProductDTO = createProductDto(1);
		final Product product1 = productService.createProduct(saveProductDTO);
		log.info("product1: " + product1.toString());
		productEquals(saveProductDTO, product1);
		localDateTimeEquals(product1.getCreationDate(), productService.getProductById(product1.getId()).getCreationDate());
	}

	@Test
    void findProductByArticle(){
		SaveProductDTO saveProductDTO = createProductDto(1);
		final Product product1 = productService.createProduct(saveProductDTO);
		log.info("product1: " + product1.toString());
		productEquals(saveProductDTO, product1);
		localDateTimeEquals(product1.getCreationDate(), productService.getProductById(product1.getId()).getCreationDate());
	}

	@Test
	void createProduct() {
		SaveProductDTO saveProductDTO = createProductDto(1);
		final Product product = productService.createProduct(saveProductDTO);
		log.info("product: " + product.toString());
		productEquals(saveProductDTO, product);
		Assertions.assertNotNull(product.getId());
		Assertions.assertNotNull(product.getCreationDate());
		Assertions.assertNull(product.getLastQuantityChangeDate());
	}

	@Test
	void updateProduct() {
		SaveProductDTO saveProductDTO = createProductDto(1);
		final Product product1 = productService.createProduct(saveProductDTO);
		log.info("product1: " + product1.toString());
		saveProductDTO.setName("Product1 updated");
		saveProductDTO.setCategory("Category1 updated");
		saveProductDTO.setDescription("Description1 updated");
		saveProductDTO.setQuantity(15);
		saveProductDTO.setPrice(1500.1);
		saveProductDTO.setArticle("Product-1 updated");
		final Product product2 = productService.updateProduct(product1.getId(), saveProductDTO);
		log.info("product2: " + product2.toString());

		Assertions.assertEquals(product1.getId(), product2.getId());
		Assertions.assertNotEquals(product1, product2);
		localDateTimeEquals(product1.getCreationDate(), product2.getCreationDate());
		Assertions.assertNull(product1.getLastQuantityChangeDate());
		Assertions.assertNotNull(product2.getLastQuantityChangeDate());
	}

	@Test
	void deleteProduct(){
		final Product product1 = productService.createProduct(createProductDto(1));
		log.info("product1: " + product1.toString());
		productService.deleteProduct(product1.getId());
		Assertions.assertThrows(ProductNotFoundExceptionById.class, () -> productService.getProductById(product1.getId()));
		Assertions.assertThrows(ProductNotFoundExceptionByArticle.class, () -> productService.getProductByArticle(product1.getArticle()));
	}
	@Test
	void testProductReadNotFound() {
		Assertions.assertThrows(ProductNotFoundExceptionById.class, () -> productService.getProductById(UUID.fromString("4a90898f-9d1d-477b-990a-e475ffb8238e")));
	}
}