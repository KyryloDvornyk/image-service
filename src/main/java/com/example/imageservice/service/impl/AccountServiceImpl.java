package com.example.imageservice.service.impl;

import com.example.imageservice.dto.request.AccountRequestDto;
import com.example.imageservice.dto.response.AccountResponseDto;
import com.example.imageservice.model.Account;
import com.example.imageservice.repository.AccountRepository;
import com.example.imageservice.service.AccountService;
import com.example.imageservice.service.mapper.impl.AccountDtoMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountDtoMapper accountDtoMapper;
    private final PasswordEncoder encoder;

    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountDtoMapper accountDtoMapper,
                              PasswordEncoder encoder) {
        this.accountRepository = accountRepository;
        this.accountDtoMapper = accountDtoMapper;
        this.encoder = encoder;
    }

    @Override
    public AccountResponseDto save(AccountRequestDto requestDto) {
        Account account = accountDtoMapper.mapToModel(requestDto);
        if (accountRepository.findByLogin(account.getLogin()).isPresent()) {
            throw new RuntimeException("Login " + account.getLogin() + " already exists");
        }
        accountRepository.save(account);
        return accountDtoMapper.mapToDto(account);
    }

    @Override
    public AccountResponseDto update(AccountRequestDto requestDto, Long id) {
        Account account = accountRepository.getById(id);
        account.setLogin(requestDto.getLogin());
        account.setPassword(encoder.encode(requestDto.getPassword()));
        accountRepository.save(account);
        return accountDtoMapper.mapToDto(account);
    }

    @Override
    public AccountResponseDto get(Long id) {
        return accountDtoMapper.mapToDto(accountRepository.getById(id));
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }
}
