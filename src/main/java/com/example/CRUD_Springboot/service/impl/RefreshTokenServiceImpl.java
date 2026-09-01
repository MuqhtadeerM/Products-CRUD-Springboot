package com.example.CRUD_Springboot.service.impl;

import com.example.CRUD_Springboot.entity.RefreshToken;
import com.example.CRUD_Springboot.entity.User;
import com.example.CRUD_Springboot.exception.InvalidCredentialsException;
import com.example.CRUD_Springboot.repository.RefreshTokenRepository;
import com.example.CRUD_Springboot.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl
        implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshExpiration;

    public RefreshTokenServiceImpl(
            RefreshTokenRepository refreshTokenRepository,
            @Value("${jwt.refresh-expiration}")
            long refreshExpiration) {

        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshExpiration = refreshExpiration;
    }

    @Override
    public RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(
                LocalDateTime.now()
                        .plusNanos(refreshExpiration * 1_000_000)
        );
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken =
                refreshTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new InvalidCredentialsException(
                                        "Invalid refresh token"));

        if (refreshToken.isRevoked()) {
            throw new InvalidCredentialsException(
                    "Refresh token has been revoked");
        }

        if (refreshToken.getExpiresAt()
                .isBefore(LocalDateTime.now())) {

            throw new InvalidCredentialsException(
                    "Refresh token has expired");
        }

        return refreshToken;
    }

    @Override
    public void revokeToken(RefreshToken refreshToken) {

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }
}