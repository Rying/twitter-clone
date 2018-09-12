package com.example.twitterclone.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UsernameAndPasswordAuthenticationToken extends AbstractAuthenticationToken {
    private String password;
    private String name;

    public UsernameAndPasswordAuthenticationToken(String name) {
        super(null);
        this.name = name;
        setAuthenticated(true);
    }

    UsernameAndPasswordAuthenticationToken(String password, String name) {
        super(null);
        this.password = password;
        this.name = name;
    }

    @Override
    public Object getCredentials() {
        return this.name;
    }

    @Override
    public Object getPrincipal() {
        return this.password;
    }
}
