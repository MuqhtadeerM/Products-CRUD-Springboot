package com.example.CRUD_Springboot.service;

import com.example.CRUD_Springboot.dto.LoginRequest;
import com.example.CRUD_Springboot.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}