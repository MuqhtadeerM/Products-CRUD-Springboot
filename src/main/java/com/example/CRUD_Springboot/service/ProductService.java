package com.example.CRUD_Springboot.service;

import com.example.CRUD_Springboot.dto.ProductRequest;
import com.example.CRUD_Springboot.dto.ProductResponse;
import com.example.CRUD_Springboot.dto.ProductUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductResponse> getProducts(Pageable pageable);

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);
    ProductResponse updateProduct(Long id, ProductUpdateRequest request);
}