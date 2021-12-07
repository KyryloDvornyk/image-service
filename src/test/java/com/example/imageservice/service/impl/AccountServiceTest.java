package com.example.imageservice.service.impl;

import java.util.Optional;
import com.example.imageservice.dto.request.AccountRequestDto;
import com.example.imageservice.model.Account;
import com.example.imageservice.repository.AccountRepository;
import com.example.imageservice.service.mapper.impl.AccountDtoMapper;
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
class AccountServiceTest {
    @Autowired
    private PasswordEncoder autowiredEncoder;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountDtoMapper accountDtoMapper;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private AccountServiceImpl accountService;
    @Captor
    ArgumentCaptor<Account> accountArgumentCaptor;

    @Test
    public void save_NotOk() {
        Account account = new Account();
        account.setLogin("login");
        AccountRequestDto dto = new AccountRequestDto();
        dto.setLogin("login");
        Mockito.when(accountDtoMapper.mapToModel(dto)).thenReturn(account);
        Mockito.when(accountRepository.findByLogin(account.getLogin()))
                .thenReturn(Optional.of(account));
        Assertions.assertThrows(RuntimeException.class, () -> accountService.save(dto));
    }

    @Test
    public void update_Ok() {
        Account account = new Account();
        account.setPassword(encoder.encode("password"));
        account.setLogin("login");
        Mockito.when(accountRepository.getById(1L)).thenReturn(account);
        String newPassword = "newPassword";
        Mockito.when(encoder.encode(newPassword))
                .thenReturn(autowiredEncoder.encode(newPassword));
        AccountRequestDto dto = new AccountRequestDto();
        dto.setLogin("newLogin");
        dto.setPassword(newPassword);
        accountService.update(dto, 1L);
        Mockito.verify(accountRepository).save(accountArgumentCaptor.capture());
        Assertions.assertEquals("newLogin", accountArgumentCaptor.getValue().getLogin());
        Assertions.assertTrue(autowiredEncoder.matches(newPassword,
                accountArgumentCaptor.getValue().getPassword()));
    }
}