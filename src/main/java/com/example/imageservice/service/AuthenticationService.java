package com.example.imageservice.service;

import com.example.imageservice.dto.response.AccountResponseDto;

public interface AuthenticationService {
    AccountResponseDto register(String login, String password);
}
