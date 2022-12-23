package com.zerobase.fintech.domain.account.service;

import com.zerobase.fintech.domain.account.dto.account.AccountDto;
import com.zerobase.fintech.domain.account.dto.account.ManageAccountDto;
import com.zerobase.fintech.domain.account.dto.transaction.DepositWithdrawDto;
import com.zerobase.fintech.domain.account.dto.transaction.ResultDto;
import com.zerobase.fintech.domain.account.entity.Account;
import com.zerobase.fintech.domain.account.entity.Transaction;
import com.zerobase.fintech.domain.account.exception.AccountException;
import com.zerobase.fintech.domain.account.repository.AccountRepository;
import com.zerobase.fintech.domain.account.repository.TransactionRepository;
import com.zerobase.fintech.domain.account.type.AccountCode;
import com.zerobase.fintech.domain.account.type.AccountErrorCode;
import com.zerobase.fintech.domain.account.type.AccountStatus;
import com.zerobase.fintech.domain.user.entity.User;
import com.zerobase.fintech.domain.user.exception.UserException;
import com.zerobase.fintech.domain.user.repository.UserRepository;
import com.zerobase.fintech.domain.user.service.user.UserService;
import com.zerobase.fintech.domain.user.type.UserErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountService {

	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final TransactionRepository transactionRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;

	public AccountService(AccountRepository accountRepository,
	                      UserRepository userRepository,
	                      PasswordEncoder passwordEncoder,
	                      UserService userService,
	                      TransactionRepository transactionRepository) {

		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
		this.transactionRepository = transactionRepository;
	}

	@Transactional
	public ManageAccountDto.CreateResponse createAccount(String username, String password, String accountPassword, Long initialBalance) {

		// 유저 정보 확인
		User user = userCheck(username, password);

		return ManageAccountDto.CreateResponse.from(AccountDto.fromEntity(accountRepository.save(Account.builder()
				.password(passwordEncoder.encode(accountPassword))
				.accountStatus(AccountStatus.REGISTERED)
				.registeredAt(LocalDateTime.now())
				.accountNumber(generateAccountNumber())
				.balance(initialBalance)
				.createdAt(LocalDateTime.now())
				.modifiedAt(LocalDateTime.now())
				.user(user)
				.build())));
	}

	@Transactional
	public ManageAccountDto.DeleteResponse deleteAccount(String username, String password, String accountNumber, String accountPassword) {

		// 유저 정보 확인
		userCheck(username, password);
		// 계좌 정보 확인
		Account account = accountCheck(accountNumber, accountPassword);

		// 이미 해지된 계좌인 경우 예외 발생
		isRegisteredAccount(account);

		account.setAccountStatus(AccountStatus.UNREGISTERED);
		account.setUnRegisteredAt(LocalDateTime.now());

		return ManageAccountDto.DeleteResponse.from(AccountDto.fromEntity(accountRepository.save(account)));
	}

	public List<DepositWithdrawDto.Response> recentTransactionHistoriesByUser(String username,
	                                                                    String password,
	                                                                    String accountNumber,
	                                                                    String accountPassword) {
		userCheck(username, password);
		Account account = accountCheck(accountNumber, accountPassword);
		PageRequest pageRequest = PageRequest.of(0, 10);
		if(transactionRepository.findAllByAccountOrderByTransactionDateDesc(account,pageRequest) == null){
			throw new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND);
		}
		Page<Transaction> list = transactionRepository.findAllByAccountOrderByTransactionDateDesc(account,pageRequest);

		List<DepositWithdrawDto.Response> dtoList = new ArrayList<>();
		for (Transaction transaction : list) {
			DepositWithdrawDto.Response result =
					DepositWithdrawDto.Response.depositWithdrawFrom(ResultDto.fromEntity(transaction));
			dtoList.add(result);
		}
		return dtoList;
	}

	public List<AccountDto> getAccountList(String username, String password) {
		User user = userCheck(username, password);
		List<Account> accountList = accountRepository.findAllByUser(user)
				.orElseThrow(() -> new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND));

		List<AccountDto> accounts = new ArrayList<>();
		for (Account account : accountList) {
			AccountDto accountDto = AccountDto.fromEntity(account);
			accounts.add(accountDto);
		}

		return accounts;
	}

	public ManageAccountDto.BalanceResponse getBalance(String username, String password, String accountNumber, String accountPassword) {
		userCheck(username, password);
		Account account = accountCheck(accountNumber, accountPassword);
		ManageAccountDto.BalanceResponse balanceResponse = ManageAccountDto.BalanceResponse.fromEntity(account);
		balanceResponse.setNowDate(LocalDateTime.now());
		return balanceResponse;
	}

	private String generateAccountNumber() {
		return accountRepository.findFirstByOrderByAccountNumberDesc().map(account
						-> (Long.parseLong(account.getAccountNumber())) + 1 + "")
				.orElse(AccountCode.INIT_CODE.getValue());
	}

	public void validateAccountPassword(Account account, String password) {
		if (!passwordEncoder.matches(password, account.getPassword())) {
			throw new AccountException(AccountErrorCode.WRONG_ACCOUNT_PASSWORD);
		}
	}

	private User userCheck(String username, String password) {
		// 계정 유무 확인
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
		// 계정 비밀번호 확인
		userService.securityPasswordCheck(username, password);
		return user;
	}

	public Account accountCheck(String accountNumber, String accountPassword) {
		// 계좌 유무 확인
		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND));

		// 계좌 비밀번호 확인
		validateAccountPassword(account, accountPassword);

		return account;
	}

	private boolean isRegisteredAccount(Account account) {
		if (account.getAccountStatus() != AccountStatus.REGISTERED) {
			throw new AccountException(AccountErrorCode.ALREADY_UNREGISTERED_ACCOUNT);
		}
		return true;
	}

	public List<ResultDto> periodTransaction(String accountNumber,
	                                         String accountPassword,
	                                         LocalDate startDt,
	                                         LocalDate endDt) {

		Account account = accountCheck(accountNumber, accountPassword);

		LocalDateTime start = startDt.atTime(0, 0, 0);
		LocalDateTime end = endDt.atTime(23, 59, 59);

		List<Transaction> list = transactionRepository.findAllByAccountAndTransactionDateBetween(account, start, end)
				.orElseThrow(() -> new AccountException(AccountErrorCode.TRANSACTION_NOT_FOUND));

		List<ResultDto> dtoList = new ArrayList<>();
		for (Transaction transaction : list) {
			dtoList.add(ResultDto.fromEntity(transaction));
		}

		return dtoList;
	}
}
