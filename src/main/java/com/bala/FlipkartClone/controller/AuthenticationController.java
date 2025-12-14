package com.bala.FlipkartClone.controller;

import com.bala.FlipkartClone.model.Users;
import com.bala.FlipkartClone.service.AuthencationService;
import com.bala.FlipkartClone.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthencationService authservice;

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        System.out.println(user.getUsername());

        return authservice.verify(user);
    }
}
