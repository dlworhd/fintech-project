package com.zerobase.fintech.account.service;

import com.zerobase.fintech.account.dto.AccountDto;
import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.AccountStatus;
import com.zerobase.fintech.account.exception.AccountException;
import com.zerobase.fintech.account.repository.AccountRepository;
import com.zerobase.fintech.account.type.AccountErrorCode;
import com.zerobase.fintech.user.entity.User;
import com.zerobase.fintech.user.repository.UserRepository;
import com.zerobase.fintech.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    // 1. 사용자 조회
    // 2. 계좌 번호 생성 후 암호화 해서 저장
    // 3. 가입 정보 반환
    public AccountDto createAccount(String username, String password, String accountPassword, Long initialBalance) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        // 1. 유저 아이디 체크
        userService.usernameCheck(username);
        // 2. 암호화된 비밀번호 체크
        userService.securityPasswordCheck(username, password);

        AccountDto accountDto = AccountDto.fromEntity(accountRepository.save(Account.builder()
                .accountNumber(generateAccountNumber())
                .user(optionalUser.get())
                .password(passwordEncoder.encode(accountPassword))
                .balance(initialBalance)
                .accountStatus(AccountStatus.ACCOUNT_REGISTERED)
                .registeredAt(LocalDateTime.now().withNano(0))
                .build()));

        return accountDto;
    }

    public AccountDto deleteAccount(String username, String password, String accountNumber, String accountPassword) {
        Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber)).orElseThrow(() -> new AccountException(AccountErrorCode.USER_NOT_FOUND));

        if (!optionalAccount.isPresent()) {
            System.out.println("값이 없습니다");
        }

        Account account = optionalAccount.get();

        // 1. 유저 아이디 체크
        userService.usernameCheck(username);
        // 2. 암호화된 비밀번호 체크
        userService.securityPasswordCheck(username, password);
        // 3. 암호화된 계좌 비밀번호 체크
        securityAccountPasswordCheck(account, accountPassword);


        account.setAccountStatus(AccountStatus.ACCOUNT_UNREGISTERED);
        account.setUnRegisteredAt(LocalDateTime.now().withNano(0));
        Account updatedAccount = accountRepository.save(account);

        return AccountDto.fromEntity(updatedAccount);
    }

    public String generateAccountNumber() {
        return accountRepository.findFirstByOrderByAccountNumberDesc().map(account
                -> (Integer.parseInt(account.getAccountNumber())) + 1 + "").orElse("82100000000");
    }

    public boolean securityAccountPasswordCheck(Account account, String accountPassword) {
        if (!passwordEncoder.matches(accountPassword, account.getPassword())) {
            throw new AccountException(AccountErrorCode.WRONG_PASSWORD);
        }
        return true;
    }
}
