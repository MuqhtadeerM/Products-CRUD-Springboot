package com.example.CRUD_Springboot.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping
    public String getProducts() {
        return "Get All Products";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id){
        return "Get Products with id:" + id;
    }

    @PostMapping
    public  String createProduct() {
        return "create product";
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
