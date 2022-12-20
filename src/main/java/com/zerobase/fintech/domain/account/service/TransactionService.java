package com.zerobase.fintech.domain.account.service;

import com.zerobase.fintech.domain.account.dto.transaction.DepositWithdrawDto;
import com.zerobase.fintech.domain.account.dto.transaction.ResultDto;
import com.zerobase.fintech.domain.account.entity.Account;
import com.zerobase.fintech.domain.account.entity.Transaction;
import com.zerobase.fintech.domain.account.entity.TransactionDetail;
import com.zerobase.fintech.domain.account.exception.AccountException;
import com.zerobase.fintech.domain.account.repository.AccountRepository;
import com.zerobase.fintech.domain.account.repository.TransactionDetailRepository;
import com.zerobase.fintech.domain.account.repository.TransactionRepository;
import com.zerobase.fintech.domain.account.type.AccountErrorCode;
import com.zerobase.fintech.domain.account.type.AccountStatus;
import com.zerobase.fintech.domain.account.type.BankServiceType;
import com.zerobase.fintech.domain.user.exception.UserException;
import com.zerobase.fintech.domain.user.repository.UserRepository;
import com.zerobase.fintech.domain.user.service.user.UserService;
import com.zerobase.fintech.domain.user.type.UserErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class TransactionService {

	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final TransactionRepository transactionRepository;
	private final AccountService accountService;
	private final TransactionDetailRepository transactionDetailRepository;


	@Transactional
	public DepositWithdrawDto.Response depositWithdraw(String username, String password, String accountNumber, String accountPassword, Long amount, BankServiceType bankServiceType) {

		// 유저 검토
		Account account = accountByValidUser(username, password, accountNumber, accountPassword);
		Account savedAccount;

		if (bankServiceType.equals(BankServiceType.DEPOSIT)) {
			account.setBalance(account.getBalance() + amount);
			account.setModifiedAt(LocalDateTime.now());
			savedAccount = accountRepository.save(account);

		} else if (bankServiceType.equals(BankServiceType.WITHDRAWAL)) {
			validBalance(account, amount);
			account.setBalance(account.getBalance() - amount);
			account.setModifiedAt(LocalDateTime.now());
			savedAccount = accountRepository.save(account);
			amount *= -1;
		} else {
			throw new AccountException(AccountErrorCode.NO_BANK_SERVICE_TYPE);
		}

		return DepositWithdrawDto.Response.from(ResultDto.depositWithdrawFromEntity(
				transactionRepository.save(Transaction.builder()
						.transactionDate(LocalDateTime.now())
						.bankServiceType(bankServiceType)
						.account(savedAccount)
						.amount(amount)
						.balanceSnapshot(savedAccount.getBalance())
						.createdAt(LocalDateTime.now())
						.modifiedAt(LocalDateTime.now())
						.build())));
	}

	public ResultDto transfer(String senderAccountNumber,
	                          String receiverAccountNumber,
	                          String accountPassword,
	                          Long amount,
	                          BankServiceType bankServiceType
	) {

		if (!bankServiceType.equals(BankServiceType.TRANSFER)) {
			throw new AccountException(AccountErrorCode.NO_BANK_SERVICE_TYPE);
		}

		Account senderAccount = accountService.accountCheck(senderAccountNumber, accountPassword);

		if (senderAccount.getAccountStatus() == AccountStatus.UNREGISTERED) {
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

		Transaction transaction = Transaction.builder()
				.bankServiceType(bankServiceType)
				.transactionDate(LocalDateTime.now())
				.account(senderAccount)
				.amount(amount * -1)
				.balanceSnapshot(senderAccount.getBalance())
				.createdAt(LocalDateTime.now())
				.modifiedAt(LocalDateTime.now())
				.transactionDetail(transactionDetailRepository.save(TransactionDetail.builder()
						.senderAccountNumber(senderAccount.getAccountNumber())
						.senderName(senderAccount.getUser().getName())
						.receiverAccountNumber(receiverAccount.getAccountNumber())
						.receiverName(receiverAccount.getUser().getName())
						.build()))
				.build();

		Transaction savedTransaction = transactionRepository.save(transaction);

		return ResultDto.transferFromEntity(savedTransaction);
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

		if (account.getAccountStatus() == AccountStatus.UNREGISTERED) {
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
