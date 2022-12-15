package com.zerobase.fintech.account.service;

import com.zerobase.fintech.account.dto.*;
import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.AccountStatus;
import com.zerobase.fintech.account.entity.Transfer;
import com.zerobase.fintech.account.entity.DepositWithdraw;
import com.zerobase.fintech.account.exception.AccountException;
import com.zerobase.fintech.account.repository.AccountRepository;
import com.zerobase.fintech.account.repository.TransferRepository;
import com.zerobase.fintech.account.repository.DepositWithdrawRepository;
import com.zerobase.fintech.account.type.AccountErrorCode;
import com.zerobase.fintech.account.type.BankServiceType;
import com.zerobase.fintech.user.exception.UserException;
import com.zerobase.fintech.user.repository.UserRepository;
import com.zerobase.fintech.user.service.UserService;
import com.zerobase.fintech.user.type.UserErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class DepositWithdrawService {

	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final DepositWithdrawRepository depositWithdrawRepository;
	private final AccountService accountService;
	private final TransferRepository transferRepository;


	@Transactional
	public DepositWithdrawInputDto.Response deposit(String username, String password, String accountNumber, String accountPassword, Long amount) {

		// 유저 검토
		Account account = accountByValidUser(username, password, accountNumber, accountPassword);

		account.setBalance(account.getBalance() + amount);
		account.setModifiedAt(LocalDateTime.now());
		Account savedAccount = accountRepository.save(account);

		return DepositWithdrawInputDto.Response.from(DepositWithdrawDto.fromEntity(
				depositWithdrawRepository.save(DepositWithdraw.builder()
						.transactionDate(LocalDateTime.now())
						.bankServiceType(BankServiceType.DEPOSIT)
						.account(savedAccount)
						.amount(amount)
						.balanceSnapshot(savedAccount.getBalance())
						.createdAt(LocalDateTime.now())
						.modifiedAt(LocalDateTime.now())
						.build())));
	}

	@Transactional
	public DepositWithdrawInputDto.Response withdraw(String username, String password, String accountNumber, String accountPassword, Long amount) {

		// 유저 검토
		Account account = accountByValidUser(username, password, accountNumber, accountPassword);
		// 잔액 부족시 예외 발생
		validBalance(account, amount);

		account.setBalance(account.getBalance() - amount);
		account.setModifiedAt(LocalDateTime.now());
		Account savedAccount = accountRepository.save(account);

		return DepositWithdrawInputDto.Response.from(DepositWithdrawDto.fromEntity(
				depositWithdrawRepository.save(DepositWithdraw.builder()
						.transactionDate(LocalDateTime.now())
						.bankServiceType(BankServiceType.WITHDRAWAL)
						.account(savedAccount)
						.amount(amount * -1)
						.balanceSnapshot(savedAccount.getBalance())
						.createdAt(LocalDateTime.now())
						.modifiedAt(LocalDateTime.now())
						.build())));
	}

	public TransferInputDto.Response transfer(String senderAccountNumber,
	                                          String receiverAccountNumber,
	                                          String accountPassword,
	                                          Long amount
	) {

		Account senderAccount = accountService.accountCheck(senderAccountNumber, accountPassword);

		if (senderAccount.getAccountStatus() == AccountStatus.ACCOUNT_UNREGISTERED) {
			throw new AccountException(AccountErrorCode.UNREGISTERED_ACCOUNT);
		}

		Account receiverAccount = accountRepository.findByAccountNumber(receiverAccountNumber)
				.orElseThrow(() -> new AccountException(AccountErrorCode.RECEIVER_ACCOUNT_NOT_FOUND));

		// 잔액 부족시 예외 발생
		validBalance(senderAccount, amount);

		senderAccount.setBalance(senderAccount.getBalance() - amount);
		receiverAccount.setBalance(receiverAccount.getBalance() + amount);

		senderAccount.setModifiedAt(LocalDateTime.now());
		receiverAccount.setModifiedAt(LocalDateTime.now());

		DepositWithdraw depositWithdraw = DepositWithdraw.builder()
				.bankServiceType(BankServiceType.TRANSFER)
				.transactionDate(LocalDateTime.now())
				.account(senderAccount)
				.amount(amount * -1)
				.balanceSnapshot(senderAccount.getBalance())
				.createdAt(LocalDateTime.now())
				.modifiedAt(LocalDateTime.now())
				.transfer(transferRepository.save(Transfer.builder()
						.senderAccountNumber(senderAccount.getAccountNumber())
						.senderName(senderAccount.getUser().getName())
						.receiverAccountNumber(receiverAccount.getAccountNumber())
						.receiverName(receiverAccount.getUser().getName())
						.build()))
				.build();

		DepositWithdraw savedDepositWithdraw = depositWithdrawRepository.save(depositWithdraw);

		return TransferInputDto.Response.from(TransferDto.fromEntity(savedDepositWithdraw));
	}

	private void validBalance(Account account, Long amount) {
		if (account.getBalance() - amount < 0) {
			throw new AccountException(AccountErrorCode.NOT_ENOUGH_BALANCE);
		}
	}

	private Account accountByValidUser(String username, String password, String accountNumber, String accountPassword) {
		// 계정 유무 확인
		userRepository.findByUsername(username)
				.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		// 계좌 유무 확인
		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND));

		if (account.getAccountStatus() == AccountStatus.ACCOUNT_UNREGISTERED) {
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

		return account;
	}


}
