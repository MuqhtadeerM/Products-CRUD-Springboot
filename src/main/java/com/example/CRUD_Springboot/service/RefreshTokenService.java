package com.example.CRUD_Springboot.service;

import com.example.CRUD_Springboot.entity.RefreshToken;
import com.example.CRUD_Springboot.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token);

    void revokeToken(RefreshToken refreshToken);
}