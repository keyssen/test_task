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

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllProducts(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "5") int size,
                                                             @RequestParam(required = false) String search) {
        try{
            Page<ViewProductDTO> products = productService.getAllProducts(page,size,search).map(ViewProductDTO::new);
            Map<String, Object> response = new HashMap<>();
            response.put("products", products.get().toList());
            response.put("currentPage", products.getNumber());
            response.put("totalItems", products.getTotalElements());
            response.put("totalPages", products.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getProductByArticle")
    public ViewProductDTO getProductByArticle(@RequestParam(value = "article", required = false) String article) {
        return new ViewProductDTO(productService.getProductByArticle(article));
    }

    @GetMapping("/{id}")
    public ViewProductDTO getProductById(@PathVariable UUID id) {
        return new ViewProductDTO(productService.getProductById(id));
    }


    @PostMapping
    public ViewProductDTO createProduct(@Valid @RequestBody SaveProductDTO productDTO) {
        return new ViewProductDTO(productService.createProduct(productDTO));
    }

    @PutMapping("/{id}")
    public ViewProductDTO updateProduct(@PathVariable UUID id, @Valid @RequestBody SaveProductDTO productDTO) {
        return new ViewProductDTO(productService.updateProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}