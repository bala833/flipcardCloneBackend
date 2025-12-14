package com.bala.FlipkartClone.service.Implemention;

import com.bala.FlipkartClone.model.Users;
import com.bala.FlipkartClone.service.AuthencationService;
import com.bala.FlipkartClone.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthenticationImp implements AuthencationService {


    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    public String verify(Users user){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        return "fail";
    }
}
