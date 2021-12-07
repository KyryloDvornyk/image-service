package com.example.imageservice.service.impl;

import com.example.imageservice.dto.request.AccountRequestDto;
import com.example.imageservice.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class AuthenticationServiceTest {
    @Autowired
    private PasswordEncoder autowiredEncoder;
    @Mock
    private AccountService accountService;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    @Captor
    private ArgumentCaptor<AccountRequestDto> dtoArgumentCaptor;

    @Test
    public void register_Ok() {
        String login = "login";
        String password = "password";
        Mockito.when(encoder.encode(password)).thenReturn(autowiredEncoder.encode(password));
        authenticationService.register(login, password);
        Mockito.verify(accountService).save(dtoArgumentCaptor.capture());
        Assertions.assertEquals(login, dtoArgumentCaptor.getValue().getLogin());
        Assertions.assertTrue(autowiredEncoder.matches(password, dtoArgumentCaptor.getValue().getPassword()));
    }
}