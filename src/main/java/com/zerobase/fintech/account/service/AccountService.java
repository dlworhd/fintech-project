package com.zerobase.fintech.account.service;

import com.zerobase.fintech.account.dto.CreateAccount;
import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.AccountStatus;
import com.zerobase.fintech.account.repository.AccountRepository;
import com.zerobase.fintech.user.entity.User;
import com.zerobase.fintech.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * 1. 사용자 조회
     * 2. 계좌 번호 생성 후 암호화 해서 저장
     * 3. 가입 정보 반환
     */

    @Transactional
    public Account createAccount(CreateAccount.Request request) {

        String encPassword = passwordEncoder.encode(request.getPassword());
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsernameAndPassword(request.getUsername(), encPassword));

        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("입력하신 아이디로 정보를 찾을 수 없습니다.");
        }

        String accountNumber = accountRepository.findFirstByOrderByAccountNumberDesc().map(account -> (Integer.parseInt(account.getAccountNumber())) + 1 + "").orElse("1000000000");

        Account savedAccount = accountRepository.save(Account.builder()
                .accountNumber("82" + accountNumber)
                .user(optionalUser.get())
                .password(request.getAccountPassword())
                .balance(request.getInitialBalance())
                .accountStatus(AccountStatus.REGISTERED)
                .registeredAt(LocalDateTime.now())
                .build());

        return savedAccount;
    }
}
