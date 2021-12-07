package com.example.imageservice.service.impl;

import com.example.imageservice.dto.request.AccountRequestDto;
import com.example.imageservice.dto.response.AccountResponseDto;
import com.example.imageservice.service.AccountService;
import com.example.imageservice.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountService accountService;
    private final PasswordEncoder encoder;

    public AuthenticationServiceImpl(AccountService accountService, PasswordEncoder encoder) {
        this.accountService = accountService;
        this.encoder = encoder;
    }

    @Override
    public AccountResponseDto register(String login, String password) {
        AccountRequestDto dto = new AccountRequestDto();
        dto.setLogin(login);
        dto.setPassword(encoder.encode(password));
        return accountService.save(dto);
    }
}
