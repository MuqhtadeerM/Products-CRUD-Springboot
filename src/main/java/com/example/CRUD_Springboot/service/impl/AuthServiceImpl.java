package com.example.CRUD_Springboot.service.impl;

import com.example.CRUD_Springboot.dto.LoginRequest;
import com.example.CRUD_Springboot.dto.LoginResponse;
import com.example.CRUD_Springboot.entity.User;
import com.example.CRUD_Springboot.exception.InvalidCredentialsException;
import com.example.CRUD_Springboot.repository.UserRepository;
import com.example.CRUD_Springboot.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.CRUD_Springboot.service.JwtService;
import com.example.CRUD_Springboot.entity.RefreshToken;
import com.example.CRUD_Springboot.service.RefreshTokenService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid username or password");
        }

        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return new LoginResponse(accessToken,
                refreshToken.getToken());
    }
}