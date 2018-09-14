package com.example.twitterclone.config;

import com.example.twitterclone.exception.AuthenticationFailedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class PasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    PasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("hello");
        try {
            AbstractAuthenticationToken authRequest = buildAuthentication(request);
            return this.authenticationManager.authenticate(authRequest);
        } catch (Exception failed) {
            throw new AuthenticationFailedException("认证失败");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.addHeader("Authorization", "abcdefg");
    }

    private AbstractAuthenticationToken buildAuthentication(HttpServletRequest request) throws IOException {
        LoginInfo loginInfo = new ObjectMapper().readValue(request.getInputStream(), LoginInfo.class);
        log.info("login info is " + loginInfo);
        return new UsernameAndPasswordAuthenticationToken(loginInfo.getName(), loginInfo.getPassword());
    }
}
