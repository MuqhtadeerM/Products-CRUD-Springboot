package com.example.CRUD_Springboot.service.impl;

import com.example.CRUD_Springboot.dto.ItemResponse;
import com.example.CRUD_Springboot.entity.Item;
import com.example.CRUD_Springboot.entity.Product;
import com.example.CRUD_Springboot.exception.ResourceNotFoundException;
import com.example.CRUD_Springboot.repository.ItemRepository;
import com.example.CRUD_Springboot.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void getItemsByProductId_shouldReturnItems() {

        Product product = new Product();

        Item item = new Item();
        item.setProduct(product);
        item.setQuantity(5);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(itemRepository.findByProduct_Id(1L))
                .thenReturn(List.of(item));

        List<ItemResponse> response =
                itemService.getItemsByProductId(1L);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(5, response.get(0).getQuantity());

        verify(productRepository).findById(1L);
        verify(itemRepository).findByProduct_Id(1L);
    }

    @Test
    void getItemsByProductId_shouldThrowException_whenProductDoesNotExist() {

        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> itemService.getItemsByProductId(999L)
                );

        assertEquals(
                "Product not found with id: 999",
                exception.getMessage()
        );

        verify(productRepository).findById(999L);
        verify(itemRepository, never()).findByProduct_Id(999L);
    }
}