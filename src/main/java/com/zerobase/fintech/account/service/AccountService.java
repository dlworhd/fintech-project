package com.zerobase.fintech.account.service;

import com.zerobase.fintech.account.dto.AccountDto;
import com.zerobase.fintech.account.dto.BalanceDto;
import com.zerobase.fintech.account.dto.DepositWithdrawDto;
import com.zerobase.fintech.account.dto.InputInfoDto;
import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.AccountStatus;
import com.zerobase.fintech.account.entity.DepositWithdraw;
import com.zerobase.fintech.account.exception.AccountException;
import com.zerobase.fintech.account.repository.AccountRepository;
import com.zerobase.fintech.account.repository.DepositWithdrawRepository;
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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountService {

	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final DepositWithdrawRepository depositWithdrawRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;

	public AccountService(AccountRepository accountRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService, DepositWithdrawRepository depositWithdrawRepository, DepositWithdrawRepository depositWithdrawRepository1) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
		this.depositWithdrawRepository = depositWithdrawRepository1;
	}

	@Transactional
	public AccountDto createAccount(String username, String password, String accountPassword, Long initialBalance) {

		// 유저 정보 확인
		User user = userCheck(username, password);

		return AccountDto.fromEntity(accountRepository.save(Account.builder()
				.password(passwordEncoder.encode(accountPassword))
				.accountStatus(AccountStatus.ACCOUNT_REGISTERED)
				.registeredAt(LocalDateTime.now())
				.accountNumber(generateAccountNumber())
				.balance(initialBalance)
				.createdAt(LocalDateTime.now())
				.modifiedAt(LocalDateTime.now())
				.user(user)
				.build()));
	}

	@Transactional
	public AccountDto deleteAccount(String username, String password, String accountNumber, String accountPassword) {

		// 유저 정보 확인
		userCheck(username, password);
		// 계좌 정보 확인
		Account account = accountCheck(accountNumber, accountPassword);

		// 이미 해지된 계좌인 경우 예외 발생
		isRegisteredAccount(account);

		account.setAccountStatus(AccountStatus.ACCOUNT_UNREGISTERED);
		account.setUnRegisteredAt(LocalDateTime.now());

		return AccountDto.fromEntity(accountRepository.save(account));
	}

	public List<DepositWithdrawDto> lookUpDepositWithdraw(String username, String password, String accountNumber, String accountPassword) {
		userCheck(username, password);
		Account account = accountCheck(accountNumber, accountPassword);
		List<DepositWithdraw> list = depositWithdrawRepository.findByAccount(account)
				.orElseThrow(() -> new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND));

		List<DepositWithdrawDto> dtoList = new ArrayList<>();
		for (DepositWithdraw depositWithdraw : list) {

			DepositWithdrawDto depositWithdrawDto = DepositWithdrawDto.fromEntity(depositWithdraw);
			depositWithdrawDto.setTransactionDate(LocalDateTime.now());
			dtoList.add(depositWithdrawDto);
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

	public BalanceDto getBalance(String username, String password, String accountNumber, String accountPassword){
		userCheck(username, password);
		Account account = accountCheck(accountNumber, accountPassword);
		BalanceDto balanceDto = BalanceDto.fromEntity(account);
		balanceDto.setNowDate(LocalDateTime.now());
		return balanceDto;
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
		if (account.getAccountStatus() != AccountStatus.ACCOUNT_REGISTERED) {
			throw new AccountException(AccountErrorCode.ALREADY_UNREGISTERED_ACCOUNT);
		}
		return true;
	}
}
