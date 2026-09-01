package com.example.CRUD_Springboot.controller;

import com.example.CRUD_Springboot.dto.ProductRequest;
import com.example.CRUD_Springboot.dto.ProductResponse;
import com.example.CRUD_Springboot.entity.Product;
import com.example.CRUD_Springboot.service.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductResponse createProduct(
            @RequestBody ProductRequest request) {

        return productService.createProduct(request);
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id) {
        return "Update product with id: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return "Delete product with id: " + id;
    }

    @GetMapping("/{id}/items")
    public String getProductItems(@PathVariable Long id) {
        return "Get items for product with id: " + id;
    }
}