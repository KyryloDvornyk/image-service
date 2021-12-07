package com.example.imageservice.security;

import com.example.imageservice.model.Account;
import com.example.imageservice.repository.AccountRepository;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Account account = accountRepository.findByLogin(login).orElseThrow(()
                -> new UsernameNotFoundException("User not found."));
        UserBuilder builder;
        builder = org.springframework.security.core.userdetails.User.withUsername(login);
        builder.password(account.getPassword());
        builder.roles();
        return builder.build();
    }
}
