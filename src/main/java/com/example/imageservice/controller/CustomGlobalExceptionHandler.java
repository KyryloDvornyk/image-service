package com.example.imageservice.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import com.example.imageservice.exception.UnauthenticatedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {UnauthenticatedAccessException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.NOT_FOUND.value());
        String message = ex.getMessage();
        body.put("errorMessage", message);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
