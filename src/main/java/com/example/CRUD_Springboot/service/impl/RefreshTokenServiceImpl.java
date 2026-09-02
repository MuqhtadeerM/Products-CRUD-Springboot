package com.example.CRUD_Springboot.service.impl;

import com.example.CRUD_Springboot.entity.RefreshToken;
import com.example.CRUD_Springboot.entity.User;
import com.example.CRUD_Springboot.exception.InvalidCredentialsException;
import com.example.CRUD_Springboot.repository.RefreshTokenRepository;
import com.example.CRUD_Springboot.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
    @Transactional(readOnly = true)
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

        // User is LAZY-loaded.
        // @Transactional keeps the persistence context open
        // while the caller accesses refreshToken.getUser().
        refreshToken.getUser().getUsername();

        return refreshToken;
    }

    @Override
    @Transactional
    public void revokeToken(RefreshToken refreshToken) {

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }
}