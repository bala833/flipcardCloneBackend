package com.bala.FlipkartClone.controller;

import com.bala.FlipkartClone.service.JWTService;
import com.bala.FlipkartClone.service.TokenBlacklistService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class TokenBlacklistController {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "Missing or invalid Authorization header";
        }

        String token = authHeader.substring(7);
        Claims claims = jwtService.extractAllClaims(token);
        Date expiry = claims.getExpiration();

        tokenBlacklistService.blacklistToken(token, expiry);
        return "Logout Successful";


    }
}
