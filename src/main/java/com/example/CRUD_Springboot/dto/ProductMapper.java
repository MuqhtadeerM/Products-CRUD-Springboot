package com.example.CRUD_Springboot.dto;

import com.example.CRUD_Springboot.entity.Product;

public class ProductMapper {

    private ProductMapper() {
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getCreatedBy(),
                product.getCreatedOn(),
                product.getModifiedBy(),
                product.getModifiedOn()
        );
    }
}