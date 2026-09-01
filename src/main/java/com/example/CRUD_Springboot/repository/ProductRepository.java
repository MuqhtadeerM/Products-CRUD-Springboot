package com.example.CRUD_Springboot.repository;

import com.example.CRUD_Springboot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}