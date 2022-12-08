package com.zerobase.fintech.account.service;

import com.zerobase.fintech.account.dto.AccountDto;
import com.zerobase.fintech.account.dto.DepositDto;
import com.zerobase.fintech.account.dto.TransactionDto;
import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.AccountStatus;
import com.zerobase.fintech.account.entity.Transaction;
import com.zerobase.fintech.account.exception.AccountException;
import com.zerobase.fintech.account.repository.AccountRepository;
import com.zerobase.fintech.account.repository.TransactionRepository;
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
public class TransactionService {


	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final TransactionRepository transactionRepository;
	private final AccountService accountService;

	public TransactionService(AccountRepository accountRepository, UserRepository userRepository, UserService userService, TransactionRepository transactionRepository, AccountService accountService) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
		this.userService = userService;
		this.transactionRepository = transactionRepository;
		this.accountService = accountService;
	}



	public DepositDto.Response deposit(String username, String password, String accountNumber, String accountPassword, Long amount) {

		// 계정 유무 확인
		userRepository.findByUsername(username)
				.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		// 계좌 유무 확인
		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND));

		if(account.getAccountStatus() == AccountStatus.ACCOUNT_UNREGISTERED){
			throw new AccountException(AccountErrorCode.UNREGISTERED_ACCOUNT);
		}

		// 계좌에 등록된 아이디와 일치하는지 검토
		if (!username.equals(account.getUser().getUsername())) {
			throw new AccountException(AccountErrorCode.INFO_NOT_MATCH);
		}



		// 비밀번호 확인
		userService.securityPasswordCheck(username, password);
		// 계좌 비밀번호 확인
		accountService.validateAccountPassword(account, accountPassword);

		account.setBalance(account.getBalance() + amount);
		account.setModifiedAt(LocalDateTime.now());
		Account savedAccount = accountRepository.save(account);

		return DepositDto.Response.from(TransactionDto.fromEntity(
				transactionRepository.save(Transaction.builder()
				.transactionDate(LocalDateTime.now())
				.transactionType(TransactionType.DEPOSIT)
				.transactionStatus(TransactionStatus.SUCCESS)
				.account(savedAccount)
				.amount(amount)
				.balanceSnapshot(savedAccount.getBalance())
				.build())));
	}
}
