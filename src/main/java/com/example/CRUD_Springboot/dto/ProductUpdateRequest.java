package com.example.CRUD_Springboot.dto;

public class ProductUpdateRequest {

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