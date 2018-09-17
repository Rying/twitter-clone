package com.example.twitterclone.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.twitterclone.exception.AuthenticationFailedException;
import com.example.twitterclone.exception.TechnicException;
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
        response.addHeader("Authorization", signAToken());
    }

    private String signAToken() {
        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            token = JWT.create()
                    .withIssuer("auth0")
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new TechnicException("Invalid signing configuration");
        }
        return token;
    }

    private AbstractAuthenticationToken buildAuthentication(HttpServletRequest request) throws IOException {
        LoginInfo loginInfo = new ObjectMapper().readValue(request.getInputStream(), LoginInfo.class);
        log.info("login info is " + loginInfo);
        return new UsernameAndPasswordAuthenticationToken(loginInfo.getName(), loginInfo.getPassword());
    }
}
