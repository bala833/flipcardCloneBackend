package com.bala.FlipkartClone.service;

import com.bala.FlipkartClone.model.Users;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;


@Service
public interface AuthencationService {
    public String verify(Users user);

    ResponseCookie varifyUser(Users request);
}
