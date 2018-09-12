package com.example.twitterclone.config;

import com.example.twitterclone.exception.AuthenticationFailedException;
import com.example.twitterclone.exception.TechnicException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class UsernameAndPasswordAuthenticationProvider implements AuthenticationProvider {
    @Resource(name = "redisTemplate")
    private HashOperations hashOperations;

    @Override
    public Authentication authenticate(Authentication authentication) {
        assertAuthenticationData(authentication);
        Optional<Object> userId = Optional.ofNullable(hashOperations.get("users", authentication.getPrincipal().toString()));
        if(!userId.isPresent() || !hashOperations.get("user:" + userId.get(), "password").equals(authentication.getCredentials().toString())) {
            throw new AuthenticationFailedException("认证失败");
        }
        authentication.setAuthenticated(true);
        return authentication;
    }

    private void assertAuthenticationData(Authentication authentication) {
        if (null == authentication.getPrincipal() || null == authentication.getCredentials()) {
            throw new TechnicException();
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernameAndPasswordAuthenticationToken.class);
    }
}
