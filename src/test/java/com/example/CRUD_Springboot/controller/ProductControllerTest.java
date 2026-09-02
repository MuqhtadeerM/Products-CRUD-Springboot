package com.example.CRUD_Springboot.controller;

import com.example.CRUD_Springboot.dto.ProductResponse;
import com.example.CRUD_Springboot.service.ItemService;
import com.example.CRUD_Springboot.service.ProductService;

import org.junit.jupiter.api.Test;
import com.example.CRUD_Springboot.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ItemService itemService;

    @Test
    void getProductById_shouldReturnProduct() throws Exception {

        ProductResponse response = new ProductResponse(
                1L,
                "Laptop",
                "admin",
                LocalDateTime.now(),
                null,
                null
        );

        when(productService.getProductById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andExpect(jsonPath("$.createdBy").value("admin"));
    }

    @Test
    void getProductById_shouldReturn404WhenNotFound() throws Exception {

        when(productService.getProductById(999L))
                .thenThrow(new ResourceNotFoundException("Product not found with id: 999"));

        mockMvc.perform(get("/api/v1/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Product not found with id: 999"));
    }


    @Test
    void createProduct_shouldReturn400ForInvalidRequest() throws Exception {

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .post("/api/v1/products")
                                .contentType("application/json")
                                .content("""
                            {
                                "productName": ""
                            }
                            """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void deleteProduct_shouldReturn204() throws Exception {

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .delete("/api/v1/products/1")
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void getProducts_shouldReturnProducts() throws Exception {

        ProductResponse product = new ProductResponse(
                1L,
                "Laptop",
                "admin",
                LocalDateTime.now(),
                null,
                null
        );

        org.springframework.data.domain.Page<ProductResponse> page =
                new org.springframework.data.domain.PageImpl<>(
                        java.util.List.of(product)
                );

        when(productService.getProducts(
                org.mockito.ArgumentMatchers.any()
        )).thenReturn(page);

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .get("/api/v1/products")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].productName").value("Laptop"));
    }

    @Test
    void deleteProduct_shouldReturn404WhenNotFound() throws Exception {

        org.mockito.Mockito.doThrow(
                new ResourceNotFoundException("Product not found with id: 999")
        ).when(productService).deleteProduct(999L);

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .delete("/api/v1/products/999")
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}