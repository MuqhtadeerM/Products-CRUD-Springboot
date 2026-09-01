package com.example.CRUD_Springboot.service;

import com.example.CRUD_Springboot.entity.User;

public interface JwtService {

    String generateAccessToken(User user);
}