package com.example.twitterclone.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.twitterclone.exception.TechnicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        verifyAToken(request.getHeader("Authorization"));
        UsernameAndPasswordAuthenticationToken usernameAndPasswordAuthenticationToken =  new UsernameAndPasswordAuthenticationToken();
        usernameAndPasswordAuthenticationToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }

    private void verifyAToken(String authorization) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT jwt = verifier.verify(authorization);
            log.info("token: {}", jwt);
        } catch (JWTVerificationException exception){
            throw new TechnicException("Invalid signature");
        }
    }
}
