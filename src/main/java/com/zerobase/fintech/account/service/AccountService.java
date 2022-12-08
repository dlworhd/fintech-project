package com.zerobase.fintech.account.service;

import com.zerobase.fintech.account.dto.*;
import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.AccountStatus;
import com.zerobase.fintech.account.entity.Transaction;
import com.zerobase.fintech.account.exception.AccountException;
import com.zerobase.fintech.account.repository.AccountRepository;
import com.zerobase.fintech.account.repository.TransactionRepository;
import com.zerobase.fintech.account.type.AccountCode;
import com.zerobase.fintech.account.type.AccountErrorCode;
import com.zerobase.fintech.account.type.TransactionStatus;
import com.zerobase.fintech.account.type.TransactionType;
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

	public AccountService(AccountRepository accountRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService, TransactionRepository transactionRepository) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
	}


	public AccountDto createAccount(String username, String password, String accountPassword, Long initialBalance) {

		// 계정 유무 확인
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		// 유저 아이디 확인
		userService.usernameCheck(username);
		// 계정 비밀번호 확인
		userService.securityPasswordCheck(username, password);

		return AccountDto.fromEntity(accountRepository.save(Account.builder()
				.password(passwordEncoder.encode(accountPassword))
				.accountStatus(AccountStatus.ACCOUNT_REGISTERED)
				.registeredAt(LocalDateTime.now())
				.accountNumber(generateAccountNumber())
				.balance(initialBalance)
				.user(user)
				.build()));
	}

	public AccountDto deleteAccount(String username, String password, String accountNumber, String accountPassword) {

		// 계정 유무 확인
		userRepository.findByUsername(username)
				.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		// 계좌 유무 확인
		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND));
		// 이미 해지된 계좌인 경우 예외 발생
		if (account.getAccountStatus() == AccountStatus.ACCOUNT_UNREGISTERED) {
			throw new AccountException(AccountErrorCode.ALREADY_UNREGISTERED_ACCOUNT);
		}
		// 유저 아이디 확인
		userService.usernameCheck(username);
		// 계정 비밀번호 확인
		userService.securityPasswordCheck(username, password);
		// 계좌 비밀번호 확인
		validateAccountPassword(account, accountPassword);

		account.setAccountStatus(AccountStatus.ACCOUNT_UNREGISTERED);
		account.setUnRegisteredAt(LocalDateTime.now());

		return AccountDto.fromEntity(accountRepository.save(account));
	}


	public String generateAccountNumber() {
		return accountRepository.findFirstByOrderByAccountNumberDesc().map(account
						-> (Long.parseLong(account.getAccountNumber())) + 1 + "")
				.orElse(AccountCode.INIT_CODE.getValue());
	}

	public void validateAccountPassword(Account account, String password) {
		if (!passwordEncoder.matches(password, account.getPassword())) {
			throw new AccountException(AccountErrorCode.WRONG_ACCOUNT_PASSWORD);
		}
	}


}
