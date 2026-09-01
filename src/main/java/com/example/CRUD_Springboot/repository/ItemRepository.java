package com.example.CRUD_Springboot.repository;

import com.example.CRUD_Springboot.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}