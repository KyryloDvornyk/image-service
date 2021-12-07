package com.example.imageservice.exception;

public class UnauthenticatedAccessException extends RuntimeException {
    public UnauthenticatedAccessException(String message) {
        super(message);
    }
}
