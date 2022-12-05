package com.zerobase.fintech.account.service;

import com.zerobase.fintech.account.dto.AccountDto;
import com.zerobase.fintech.account.dto.CreateAccount;
import com.zerobase.fintech.account.dto.DeleteAccount;
import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.AccountStatus;
import com.zerobase.fintech.account.repository.AccountRepository;
import com.zerobase.fintech.account.type.ErrorCode;
import com.zerobase.fintech.user.entity.User;
import com.zerobase.fintech.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    public AccountDto createAccount(String username, String password, String accountPassword, Long initialBalance) {
        Optional<User> optionalUser = userRepository.findByUsername(username);


        if(!passwordEncoder.matches(password, userRepository.findByUsername(username).get().getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String accountNumber = accountRepository.findFirstByOrderByAccountNumberDesc().map(account
                -> (Integer.parseInt(account.getAccountNumber())) + 1 + "").orElse("82100000000");

        AccountDto accountDto = AccountDto.fromEntity(accountRepository.save(Account.builder()
                .accountNumber(accountNumber)
                .user(optionalUser.get())
                .password(passwordEncoder.encode(accountPassword))
                .balance(initialBalance)
                .accountStatus(AccountStatus.REGISTERED)
                .registeredAt(LocalDateTime.now().withNano(0))
                .build()));

        return accountDto;
    }

//    public AccountDto deleteAccount(String username, String password, String accountNumber, String accountPassword){
//        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(()
//                -> new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")));
//
//        if(!passwordEncoder.matches(password, optionalUser.get().getPassword())){
//            throw new RuntimeException("비밀번호가 올바르지 않습니다.");
//        }
//
//         Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber)
//                 .orElseThrow(() -> new RuntimeException("잘못된 계좌 번호입니다.")));
//
//        if(!passwordEncoder.matches(accountPassword, optionalAccount.get().getPassword())){
//            throw new RuntimeException("계좌 비밀번호를 틀렸습니다.");
//        }
//
//        AccountDto accountDto = AccountDto.fromEntity(accountRepository.delete(Account.builder()
//        return AccountDto.builder().
//
//    }
}
