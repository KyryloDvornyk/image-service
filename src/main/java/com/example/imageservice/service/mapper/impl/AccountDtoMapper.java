package com.example.imageservice.service.mapper.impl;

import java.util.stream.Collectors;
import com.example.imageservice.dto.request.AccountRequestDto;
import com.example.imageservice.dto.response.AccountResponseDto;
import com.example.imageservice.model.Account;
import com.example.imageservice.model.Image;
import com.example.imageservice.service.mapper.RequestDtoMapper;
import com.example.imageservice.service.mapper.ResponseDtoMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoMapper implements RequestDtoMapper<Account, AccountRequestDto>,
        ResponseDtoMapper<AccountResponseDto, Account> {
    @Override
    public Account mapToModel(AccountRequestDto dto) {
        Account account = new Account();
        account.setLogin(dto.getLogin());
        account.setPassword(dto.getPassword());
        return account;
    }

    @Override
    public AccountResponseDto mapToDto(Account model) {
        AccountResponseDto dto = new AccountResponseDto();
        dto.setLogin(model.getLogin());
        dto.setId(model.getId());
        dto.setCreationDateTime(model.getCreationDateTime());
        dto.setLastUpdateDateTime(model.getLastUpdateDateTime());
        dto.setImageIds(model.getImages().stream()
                .map(Image::getId)
                .collect(Collectors.toList()));
        return dto;
    }
}
