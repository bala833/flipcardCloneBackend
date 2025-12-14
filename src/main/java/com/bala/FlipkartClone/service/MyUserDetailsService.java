package com.bala.FlipkartClone.service;

import com.bala.FlipkartClone.model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface MyUserDetailsService {

    public Users register(Users user);

    public List<Users> getUserList();
}
