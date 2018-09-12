package com.example.twitterclone.config;

import com.example.twitterclone.exception.AuthenticationFailedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class PasswordAuthenticationFilter extends OncePerRequestFilter {
    private static final String API_LOGIN = "/api/login";
    private AuthenticationManager authenticationManager;

    PasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getMethod().equalsIgnoreCase("post") || !request.getRequestURI().equals(API_LOGIN)) {
            filterChain.doFilter(request, response);
            return;
        }
        final Authentication authResult;
        try {
            AbstractAuthenticationToken authRequest = buildAuthentication(request);
            authResult = this.authenticationManager.authenticate(authRequest);
        } catch (Exception failed) {
            throw new AuthenticationFailedException("认证失败");
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);
        filterChain.doFilter(request, response);
    }

    private AbstractAuthenticationToken buildAuthentication(HttpServletRequest request) throws IOException {
        LoginInfo loginInfo = new ObjectMapper().readValue(request.getInputStream(), LoginInfo.class);
        log.info("login info is " + loginInfo);
        return new UsernameAndPasswordAuthenticationToken(loginInfo.getName(), loginInfo.getPassword());
    }
}
