package com.zerobase.fintech.account.service;

import com.zerobase.fintech.account.dto.CreateAccountDto;
import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createAccount(CreateAccountDto createAccountDto) {
        Account account = Account.of(createAccountDto);
        Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findFirstByOrderByIdDesc());

        if(optionalAccount.isPresent()){
            account.setAccountNumber(passwordEncoder.encode(String.valueOf(optionalAccount.get().getId() + 1)));
        } else {
            account.setAccountNumber(passwordEncoder.encode("1"));
        }
        accountRepository.save(account);
    }
}
