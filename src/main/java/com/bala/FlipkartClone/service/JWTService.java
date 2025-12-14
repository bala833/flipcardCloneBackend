package com.bala.FlipkartClone.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface JWTService {
    public String generateToken(String user);

    String extractUserName(String token);

    boolean validateToken(String token, UserDetails userDetails);

    public Claims extractAllClaims(String token);
}
