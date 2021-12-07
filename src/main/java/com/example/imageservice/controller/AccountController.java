package com.example.imageservice.controller;

import com.example.imageservice.dto.request.AccountRequestDto;
import com.example.imageservice.dto.response.AccountResponseDto;
import com.example.imageservice.service.AccountService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public AccountResponseDto create(@RequestBody AccountRequestDto dto) {
        return accountService.save(dto);
    }

    @GetMapping("/{id}")
    public AccountResponseDto read(@PathVariable Long id) {
        return accountService.get(id);
    }

    @PutMapping("/{id}")
    public AccountResponseDto update(@PathVariable Long id, @RequestBody AccountRequestDto dto) {
        return accountService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }
}
