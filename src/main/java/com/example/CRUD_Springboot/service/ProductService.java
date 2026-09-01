package com.example.CRUD_Springboot.service;

import com.example.CRUD_Springboot.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();

}