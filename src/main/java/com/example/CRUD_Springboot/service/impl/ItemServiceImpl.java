package com.example.CRUD_Springboot.service.impl;

import com.example.CRUD_Springboot.dto.ItemResponse;
import com.example.CRUD_Springboot.entity.Item;
import com.example.CRUD_Springboot.exception.ResourceNotFoundException;
import com.example.CRUD_Springboot.repository.ItemRepository;
import com.example.CRUD_Springboot.repository.ProductRepository;
import com.example.CRUD_Springboot.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    public ItemServiceImpl(
            ItemRepository itemRepository,
            ProductRepository productRepository) {
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ItemResponse> getItemsByProductId(Long productId) {

        // First check whether product exists
        productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + productId));

        List<Item> items = itemRepository.findByProduct_Id(productId);

        return items.stream()
                .map(item -> new ItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getQuantity()
                ))
                .toList();
    }
}