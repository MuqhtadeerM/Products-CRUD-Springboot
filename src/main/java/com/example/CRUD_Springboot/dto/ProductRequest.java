package com.example.CRUD_Springboot.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String productName;

    public ProductRequest() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}