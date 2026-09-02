package com.example.CRUD_Springboot.service.impl;

import com.example.CRUD_Springboot.dto.ProductResponse;
import com.example.CRUD_Springboot.entity.Product;
import com.example.CRUD_Springboot.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.CRUD_Springboot.exception.ResourceNotFoundException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;

import org.springframework.data.domain.Page;
import java.time.LocalDateTime;
import java.util.Optional;
import com.example.CRUD_Springboot.dto.ProductUpdateRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.CRUD_Springboot.dto.ProductRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.mockito.MockedStatic;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getProductById_shouldReturnProduct() {

        Product product = new Product();

        product.setProductName("Laptop");
        product.setCreatedBy("admin");
        product.setCreatedOn(LocalDateTime.now());

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponse response =
                productService.getProductById(1L);

        assertNotNull(response);
        assertEquals("Laptop", response.getProductName());
        assertEquals("admin", response.getCreatedBy());

        verify(productRepository).findById(1L);
    }

    @Test
    void getProductById_shouldThrowException_whenProductDoesNotExist() {

        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> productService.getProductById(999L)
                );

        assertEquals(
                "Product with id 999 not found",
                exception.getMessage()
        );

        verify(productRepository).findById(999L);
    }

    @Test
    void createProduct_shouldCreateProduct() {

        ProductRequest request = new ProductRequest();
        request.setProductName("Laptop");

        Product savedProduct = new Product();
        savedProduct.setProductName("Laptop");
        savedProduct.setCreatedBy("admin");
        savedProduct.setCreatedOn(LocalDateTime.now());

        when(productRepository.save(any(Product.class)))
                .thenReturn(savedProduct);

        try (MockedStatic<SecurityContextHolder> mockedSecurity =
                     mockStatic(SecurityContextHolder.class)) {

            Authentication authentication =
                    mock(Authentication.class);

            when(authentication.getName())
                    .thenReturn("admin");

            SecurityContext securityContext =
                    mock(SecurityContext.class);

            when(securityContext.getAuthentication())
                    .thenReturn(authentication);

            mockedSecurity.when(SecurityContextHolder::getContext)
                    .thenReturn(securityContext);

            ProductResponse response =
                    productService.createProduct(request);

            assertNotNull(response);
            assertEquals("Laptop", response.getProductName());
            assertEquals("admin", response.getCreatedBy());

            verify(productRepository).save(any(Product.class));
        }
    }

    @Test
    void updateProduct_shouldUpdateProduct() {

        ProductUpdateRequest request = new ProductUpdateRequest();
        request.setProductName("Updated Laptop");

        Product existingProduct = new Product();
        existingProduct.setProductName("Laptop");
        existingProduct.setCreatedBy("admin");
        existingProduct.setCreatedOn(LocalDateTime.now());

        Product savedProduct = new Product();
        savedProduct.setProductName("Updated Laptop");
        savedProduct.setCreatedBy("admin");
        savedProduct.setCreatedOn(existingProduct.getCreatedOn());
        savedProduct.setModifiedBy("admin");
        savedProduct.setModifiedOn(LocalDateTime.now());

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(existingProduct));

        when(productRepository.save(existingProduct))
                .thenReturn(savedProduct);

        try (MockedStatic<SecurityContextHolder> mockedSecurity =
                     mockStatic(SecurityContextHolder.class)) {

            Authentication authentication = mock(Authentication.class);
            when(authentication.getName()).thenReturn("admin");

            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication())
                    .thenReturn(authentication);

            mockedSecurity.when(SecurityContextHolder::getContext)
                    .thenReturn(securityContext);

            ProductResponse response =
                    productService.updateProduct(1L, request);

            assertNotNull(response);
            assertEquals("Updated Laptop", response.getProductName());
            assertEquals("admin", response.getModifiedBy());
            assertNotNull(response.getModifiedOn());

            verify(productRepository).findById(1L);
            verify(productRepository).save(existingProduct);
        }
    }

    @Test
    void deleteProduct_shouldDeleteProduct_whenProductExists() {

        Product product = new Product();
        product.setProductName("Laptop");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).findById(1L);
        verify(productRepository).delete(product);
    }

    @Test
    void deleteProduct_shouldThrowException_whenProductDoesNotExist() {

        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> productService.deleteProduct(999L)
                );

        assertEquals(
                "Product with id 999 not found",
                exception.getMessage()
        );

        verify(productRepository).findById(999L);
        verify(productRepository, never()).delete(any(Product.class));
    }

    @Test
    void getProducts_shouldReturnPaginatedProducts() {

        Product product1 = new Product();
        product1.setProductName("Laptop");
        product1.setCreatedBy("admin");
        product1.setCreatedOn(LocalDateTime.now());

        Product product2 = new Product();
        product2.setProductName("Mobile");
        product2.setCreatedBy("admin");
        product2.setCreatedOn(LocalDateTime.now());

        PageRequest pageable = PageRequest.of(0, 2);

        Page<Product> productPage =
                new PageImpl<>(
                        List.of(product1, product2),
                        pageable,
                        2
                );

        when(productRepository.findAll(pageable))
                .thenReturn(productPage);

        Page<ProductResponse> response =
                productService.getProducts(pageable);

        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        assertEquals(2, response.getTotalElements());
        assertEquals(1, response.getTotalPages());

        assertEquals(
                "Laptop",
                response.getContent().get(0).getProductName()
        );

        assertEquals(
                "Mobile",
                response.getContent().get(1).getProductName()
        );

        verify(productRepository).findAll(pageable);
    }
}