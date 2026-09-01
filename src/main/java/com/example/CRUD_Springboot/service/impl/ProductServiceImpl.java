package com.example.CRUD_Springboot.service.impl;
import com.example.CRUD_Springboot.exception.ResourceNotFoundException;
import com.example.CRUD_Springboot.dto.ProductMapper;
import com.example.CRUD_Springboot.dto.ProductRequest;
import com.example.CRUD_Springboot.dto.ProductResponse;
import com.example.CRUD_Springboot.entity.Product;
import com.example.CRUD_Springboot.repository.ProductRepository;
import com.example.CRUD_Springboot.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.CRUD_Springboot.dto.ProductUpdateRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductResponse> getProducts(Pageable pageable) {

        Page<Product> products = productRepository.findAll(pageable);

        return products.map(ProductMapper::toResponse);
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

    @Override
    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product with id " + id + " not found"
                        )
                );

        return ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(
            Long id,
            ProductUpdateRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product with id " + id + " not found"
                        )
                );

        product.setProductName(request.getProductName());
        product.setModifiedBy("system");
        product.setModifiedOn(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.toResponse(updatedProduct);
    }
}