package com.bala.FlipkartClone.service.Implemention;

import com.bala.FlipkartClone.service.TokenBlacklistService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistImp implements TokenBlacklistService {

    private final Map<String, Date> blacklist = new ConcurrentHashMap<>();

    public void blacklistToken(String token, Date expiryData) {
        blacklist.put(token, expiryData);
    }


    public boolean isTokenBlackListed(String token) {
        if (!blacklist.containsKey(token)) return false;

        Date expiry = blacklist.get(token);
        if (expiry.before(new Date())) {
            blacklist.remove(token); // cleanup expired token
            return false;
        }
        return true;
    }
}
