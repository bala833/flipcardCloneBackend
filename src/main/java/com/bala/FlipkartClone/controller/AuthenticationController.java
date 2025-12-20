package com.bala.FlipkartClone.controller;

import com.bala.FlipkartClone.model.Users;
import com.bala.FlipkartClone.service.AuthencationService;
import com.bala.FlipkartClone.service.JWTService;
import com.bala.FlipkartClone.service.MyUserDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthencationService authservice;

    @Autowired
    private JWTService jwtService;


    @Autowired
    AuthenticationManager authManager;

//    @PostMapping("/login")
//    public String login(@RequestBody Users user) {
//        System.out.println(user.getUsername());
//
//        return authservice.verify(user);
//    }

    @PostMapping("/login/user")
    public ResponseEntity<?> loginUser(@RequestBody Users request) {


        ResponseCookie cookie1 = authservice.varifyUser(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie1.toString())
                .body("Login successful");
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Users user,
            HttpServletResponse response) {

        // TODO: validate username & password properly
//        if (!username.equals("admin") || !password.equals("password")) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }

//        validate the user with username and password and then let the cookie to set
//        try {
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            System.out.println(authentication + " : vvvvvvvv");
//        } catch (AuthenticationException e) {
//            System.out.println("error on login please check your credential");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }


        String token = jwtService.generateToken(user.getUsername());
        System.out.println("in controller" + token);

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // HTTPS only
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1 hour
//        cookie.setAttribute("SameSite", "Strict");
        cookie.setAttribute("SameSite", "Lax");

        response.addCookie(cookie);

        return ResponseEntity.ok("Logged in");
    }


    @PostMapping("/logout/user")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // true only for production
        cookie.setPath("/");
        cookie.setMaxAge(0); // delete

        response.addCookie(cookie);
        return ResponseEntity.ok("Logged out");
    }


    @GetMapping("/authenticate")
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(Map.of(
                "username", authentication.getName(),
                "loggedIn", true
        ));
    }


}
