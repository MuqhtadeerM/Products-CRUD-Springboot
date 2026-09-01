package com.example.CRUD_Springboot.dto;

public class ProductRequest {

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
