package com.example.CRUD_Springboot.dto;
import jakarta.validation.constraints.NotBlank;
public class ProductUpdateRequest {
    @NotBlank(message = "Product name is required")
    private String productName;

    public ProductUpdateRequest() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}