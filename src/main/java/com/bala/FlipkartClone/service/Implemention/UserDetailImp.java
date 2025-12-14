package com.bala.FlipkartClone.service.Implemention;

import com.bala.FlipkartClone.model.UserPrincipal;
import com.bala.FlipkartClone.model.Users;
import com.bala.FlipkartClone.repository.UserRepository;
import com.bala.FlipkartClone.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserDetailImp  implements UserDetailsService, MyUserDetailsService {

    @Autowired
    private UserRepository userrepo;





    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Trying to load user: " + username);
        Users user = userrepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        System.out.println("Found user: " + user.getUsername() + " with password: " + user.getPassword());
        return new UserPrincipal(user);
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users users) {
        users.setUsername(users.getUsername());
        users.setPassword(encoder.encode(users.getPassword()));
        userrepo.save(users);
        return users;
    }

    public List<Users> getUserList() {
        return userrepo.findAll();
    }







}
