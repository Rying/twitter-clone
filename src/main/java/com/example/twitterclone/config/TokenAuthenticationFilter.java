package com.example.twitterclone.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(!token.equals("abcdefg")) {
            filterChain.doFilter(request, response);
        } else {
            UsernameAndPasswordAuthenticationToken usernameAndPasswordAuthenticationToken =  new UsernameAndPasswordAuthenticationToken();
            usernameAndPasswordAuthenticationToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordAuthenticationToken);
            filterChain.doFilter(request, response);
        }

    }
}
