package com.example.CRUD_Springboot.service.impl;

import com.example.CRUD_Springboot.dto.LoginRequest;
import com.example.CRUD_Springboot.dto.LoginResponse;
import com.example.CRUD_Springboot.entity.User;
import com.example.CRUD_Springboot.repository.UserRepository;
import com.example.CRUD_Springboot.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid username or password");
        }

        // JWT will be generated here in the next step
        return new LoginResponse("TEMPORARY_TOKEN");
    }
}