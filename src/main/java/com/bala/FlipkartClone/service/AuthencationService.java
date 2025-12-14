package com.bala.FlipkartClone.service;

import com.bala.FlipkartClone.model.Users;
import org.springframework.stereotype.Service;


@Service
public interface AuthencationService {
    public String verify(Users user);
}
