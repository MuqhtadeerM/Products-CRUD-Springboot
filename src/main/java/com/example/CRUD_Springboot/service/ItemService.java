package com.example.CRUD_Springboot.service;

import com.example.CRUD_Springboot.dto.ItemResponse;

import java.util.List;

public interface ItemService {

    List<ItemResponse> getItemsByProductId(Long productId);
}