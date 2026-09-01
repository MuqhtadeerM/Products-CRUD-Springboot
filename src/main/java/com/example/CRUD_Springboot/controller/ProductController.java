package com.example.CRUD_Springboot.controller;

import com.example.CRUD_Springboot.entity.Product;
import com.example.CRUD_Springboot.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id) {
        return "Get product with id: " + id;
    }

    @PostMapping
    public String createProduct() {
        return "Create product";
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