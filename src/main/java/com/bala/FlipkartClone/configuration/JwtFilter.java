package com.bala.FlipkartClone.configuration;

import com.bala.FlipkartClone.service.Implemention.UserDetailImp;
import com.bala.FlipkartClone.service.JWTService;
import com.bala.FlipkartClone.service.MyUserDetailsService;
import com.bala.FlipkartClone.service.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
    ApplicationContext context;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            try {
                username = jwtService.extractUserName(token);
                System.out.println(username + "after getting user name ");
            } catch (Exception e) {
                // malformed or expired token
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                response.getWriter().write("invalid or expired token");
                return;
            }

        }

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
