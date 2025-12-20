package com.bala.FlipkartClone.configuration;

import com.bala.FlipkartClone.service.Implemention.JWTImp;
import com.bala.FlipkartClone.service.Implemention.UserDetailImp;
import com.bala.FlipkartClone.service.JWTService;
import com.bala.FlipkartClone.service.MyUserDetailsService;
import com.bala.FlipkartClone.service.TokenBlacklistService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {


    @Autowired
    private JWTService jwtService;

    @Autowired
    private JWTImp jwtImp;

    @Autowired
    ApplicationContext context;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;


    public String getJwtFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    try {
                        return  cookie.getValue();

//                        UsernamePasswordAuthenticationToken auth =
//                                new UsernamePasswordAuthenticationToken(
//                                        username, null, List.of());
//
//                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } catch (Exception e) {
                        // Invalid JWT → ignore, user unauthenticated
                        System.out.println(e + "   getting error");
                    }
                }
            }
        }
        return null;
    }
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println("JWT FILTER HID: " + path);

        //        String authHeader = request.getHeader("Authorization");
        String token = getJwtFromRequest(request);
        System.out.println("Token from cookie: " + token);
        String username = null;


        // Skip authentication for login endpoints
//        if (path.startsWith("/api/login")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        // this is added becuase to unvalidate the endpoint which is added WebLoginSecurity file where provided url to skip the authentication
        if (token == null || token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }


//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);

//            try {
//                username = jwtService.extractUserName(token);
//                System.out.println(username + "after getting user name ");
//            } catch (Exception e) {
//                // malformed or expired token
//                response.setStatus(HttpServletResponse.SC_ACCEPTED);
//                response.getWriter().write("invalid or expired token");
//                return;
//            }


        try {
            username = jwtImp.validateAndGetUsername(token);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
            return;
        } catch (JwtException | IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println(e + "this is error");
            response.getWriter().write("Invalid token");
            return;
        }
//        }

//        It seems you wanted to implement logout by invalidating the token.
//                But JWTs are stateless; you cannot detect logout unless you:
//        Maintain a token blacklist (DB/Redis)
//        Or use token versioning
//        But the block you wrote incorrectly flags all tokens as logged out.
//        System.out.println(token + "Here getting token check");
//        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            response.setStatus(HttpServletResponse.SC_ACCEPTED);
//            response.getWriter().write("token has been logged out successful");
//            return;
//        }

        // alwayas verify if the token is not present in blacklist ConcurrentHashMap inside if present then return logout

         if (token != null && tokenBlacklistService.isTokenBlackListed(token)) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write("token has been logged out successful");
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(UserDetailImp.class).loadUserByUsername(username);

          if (jwtService.validateToken(token, userDetails)) {
              UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                      new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
              usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

              SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
          }
        }

        filterChain.doFilter(request, response);
    }
}
