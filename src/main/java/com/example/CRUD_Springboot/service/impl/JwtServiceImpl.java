package com.example.CRUD_Springboot.service.impl;

import com.example.CRUD_Springboot.entity.User;
import com.example.CRUD_Springboot.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtServiceImpl(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {

        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        this.expiration = expiration;
    }

    @Override
    public String generateAccessToken(User user) {

        Date now = new Date();

        Date expiry = new Date(
                now.getTime() + expiration
        );

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role", user.getRole())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }
}