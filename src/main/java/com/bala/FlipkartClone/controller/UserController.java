package com.bala.FlipkartClone.controller;

import com.bala.FlipkartClone.model.Users;
import com.bala.FlipkartClone.service.AuthencationService;
import com.bala.FlipkartClone.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private MyUserDetailsService service;

    @Autowired
    private AuthencationService authservice;

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        System.out.println("register endpoint is calling " + user.getUsername());
     return service.register(user);
    }

    @GetMapping("/users-list")
    public List<Users> userList() {
        System.out.println("user list page");
        return service.getUserList();
    }


}
