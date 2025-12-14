package com.bala.FlipkartClone.service;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface TokenBlacklistService {

    public void blacklistToken(String token, Date expiryData);

    public boolean isTokenBlackListed(String token);
}
