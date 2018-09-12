package com.example.twitterclone.exception;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException() {
        super();
    }

    public AuthenticationFailedException(String message) {
        super(message);
    }
}
