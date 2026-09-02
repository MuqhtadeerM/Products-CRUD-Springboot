package com.example.CRUD_Springboot.controller;

import com.example.CRUD_Springboot.dto.LoginRequest;
import com.example.CRUD_Springboot.dto.LoginResponse;
import com.example.CRUD_Springboot.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.example.CRUD_Springboot.dto.RefreshTokenRequest;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }

    @PostMapping("/refresh")
    public LoginResponse refresh(
            @Valid @RequestBody RefreshTokenRequest request) {

        return authService.refreshToken(
                request.getRefreshToken()
        );
    }
}