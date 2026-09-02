package com.example.CRUD_Springboot.repository;

import com.example.CRUD_Springboot.entity.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveProduct_shouldPersistProduct() {
        Product product = new Product();
        product.setProductName("Laptop");
        product.setCreatedBy("admin");
        product.setCreatedOn(LocalDateTime.now());

        Product saved = productRepository.save(product);

        assertNotNull(saved.getId());
        assertEquals("Laptop", saved.getProductName());
    }

    @Test
    void findById_shouldReturnProduct() {
        Product product = new Product();
        product.setProductName("Phone");
        product.setCreatedBy("admin");
        product.setCreatedOn(LocalDateTime.now());

        Product saved = productRepository.save(product);

        Optional<Product> result = productRepository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("Phone", result.get().getProductName());
    }
}