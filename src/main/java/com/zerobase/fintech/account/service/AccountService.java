package com.zerobase.fintech.account.service;

import com.zerobase.fintech.account.dto.AccountDto;
import com.zerobase.fintech.account.dto.CreateAccount;
import com.zerobase.fintech.account.dto.DeleteAccount;
import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.AccountStatus;
import com.zerobase.fintech.account.exception.AccountException;
import com.zerobase.fintech.account.repository.AccountRepository;
import com.zerobase.fintech.account.type.AccountCode;
import com.zerobase.fintech.account.type.AccountErrorCode;
import com.zerobase.fintech.user.entity.User;
import com.zerobase.fintech.user.exception.UserException;
import com.zerobase.fintech.user.repository.UserRepository;
import com.zerobase.fintech.user.service.UserService;
import com.zerobase.fintech.user.type.UserErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public AccountDto createAccount(CreateAccount.Request request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        // 1. 유저 아이디 체크
        userService.usernameCheck(request.getUsername());
        // 2. 암호화된 비밀번호 체크
        userService.securityPasswordCheck(request.getUsername(), request.getPassword());

        Account account = Account.from(request);

        account.setPassword(passwordEncoder.encode(request.getAccountPassword()));
        account.setAccountStatus(AccountStatus.ACCOUNT_REGISTERED);
        account.setAccountNumber(generateAccountNumber());
        account.setUser(user);

        return AccountDto.fromEntity(accountRepository.save(account));
    }

    public AccountDto deleteAccount(DeleteAccount.Request request) {

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND));
        if(account.getAccountStatus() == AccountStatus.ACCOUNT_UNREGISTERED){
            throw new AccountException(AccountErrorCode.ALREADY_UNREGISTERED_ACCOUNT);
        }

        // 1. 유저 아이디 체크
        userService.usernameCheck(request.getUsername());
        // 2. 암호화된 비밀번호 체크
        userService.securityPasswordCheck(request.getUsername(), request.getPassword());
        // 3. 암호화된 계좌 비밀번호 체크
        securityAccountPasswordCheck(account, request.getAccountPassword());


        account.setAccountStatus(AccountStatus.ACCOUNT_UNREGISTERED);
        account.setUnRegisteredAt(LocalDateTime.now());
        Account updatedAccount = accountRepository.save(account);

        return AccountDto.fromEntity(updatedAccount);
    }

    // 계좌 생성
    public String generateAccountNumber() {
        return accountRepository.findFirstByOrderByAccountNumberDesc().map(account
                        -> (Long.parseLong(account.getAccountNumber())) + 1 + "")
                .orElse(AccountCode.INIT_CODE.getValue());
    }

    public void securityAccountPasswordCheck(Account account, String password) {
        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new AccountException(AccountErrorCode.WRONG_ACCOUNT_PASSWORD);
        }
    }
}
