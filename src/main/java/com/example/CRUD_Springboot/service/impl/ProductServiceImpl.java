package com.example.CRUD_Springboot.service.impl;

import com.example.CRUD_Springboot.dto.ProductMapper;
import com.example.CRUD_Springboot.dto.ProductRequest;
import com.example.CRUD_Springboot.dto.ProductResponse;
import com.example.CRUD_Springboot.entity.Product;
import com.example.CRUD_Springboot.repository.ProductRepository;
import com.example.CRUD_Springboot.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        Product product = new Product();

        product.setProductName(request.getProductName());
        product.setCreatedBy("system");
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }
}