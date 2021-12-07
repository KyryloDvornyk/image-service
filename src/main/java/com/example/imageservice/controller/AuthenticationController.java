package com.example.imageservice.controller;

import com.example.imageservice.dto.request.AccountRequestDto;
import com.example.imageservice.dto.response.AccountResponseDto;
import com.example.imageservice.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public AccountResponseDto register(@RequestBody AccountRequestDto dto) {
        return authenticationService.register(dto.getLogin(), dto.getPassword());
    }
}
