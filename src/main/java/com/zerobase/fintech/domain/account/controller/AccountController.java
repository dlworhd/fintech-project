package com.zerobase.fintech.domain.account.controller;

import com.zerobase.fintech.domain.account.dto.account.IdentifyAccountDto;
import com.zerobase.fintech.domain.account.dto.account.ManageAccountDto;
import com.zerobase.fintech.domain.account.dto.transaction.DepositWithdrawDto;
import com.zerobase.fintech.domain.account.dto.transaction.TransferDto;
import com.zerobase.fintech.domain.account.service.AccountService;
import com.zerobase.fintech.domain.account.service.TransactionService;
import com.zerobase.fintech.domain.user.dto.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

	private final AccountService accountService;
	private final TransactionService transactionService;

	@PostMapping
	public ResponseEntity<ManageAccountDto.CreateResponse> createAccount(@RequestBody @Valid ManageAccountDto.CreateRequest request) {
		return new ResponseEntity(accountService.createAccount(
				request.getUsername(),
				request.getPassword(),
				request.getAccountPassword(),
				request.getInitialBalance()), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<ManageAccountDto.DeleteResponse> deleteAccount(@RequestBody @Valid ManageAccountDto.DeleteRequest request) {
		return new ResponseEntity(accountService.deleteAccount(
				request.getUsername(),
				request.getPassword(),
				request.getAccountNumber(),
				request.getAccountPassword()), HttpStatus.OK);
	}

	// 입출금 내역
	@GetMapping("/histories")
	public ResponseEntity<?> recentTransactionHistoriesByUser(@RequestBody @Valid IdentifyAccountDto identifyAccountDto) {
		return new ResponseEntity<>(accountService.recentTransactionHistoriesByUser(identifyAccountDto.getUsername(),
				identifyAccountDto.getPassword(),
				identifyAccountDto.getAccountNumber(),
				identifyAccountDto.getAccountPassword()
		), HttpStatus.OK);
	}

	// 계좌 목록
	@GetMapping("/lists")
	public ResponseEntity<?> myAccounts(@RequestBody @Valid Login login) {
		return new ResponseEntity<>(accountService.getAccountList(
				login.getUsername(),
				login.getPassword()
		), HttpStatus.OK);
	}

	// 잔액 확인
	@GetMapping("/balance")
	public ResponseEntity<?> balance(@RequestBody @Valid IdentifyAccountDto identifyAccountDto) {
		return new ResponseEntity<>(accountService.getBalance(
				identifyAccountDto.getUsername(),
				identifyAccountDto.getPassword(),
				identifyAccountDto.getAccountNumber(),
				identifyAccountDto.getAccountPassword()
		), HttpStatus.OK);
	}

	// 기간 거래 조회
	@PostMapping("/period")
	public ResponseEntity<?> recentTransactionHistoriesBetweenPeriod(@RequestBody @Valid ManageAccountDto.PeriodRequest request) {
		return new ResponseEntity<>(accountService.periodTransaction(
				request.getAccountNumber(),
				request.getAccountPassword(),
				request.getStartDt(),
				request.getEndDt()), HttpStatus.OK);
	}

	@PostMapping("/deposit")
	public ResponseEntity<?> deposit(@RequestBody @Valid DepositWithdrawDto.Request request) {
		return new ResponseEntity(transactionService.depositWithdraw(
				request.getUsername(),
				request.getPassword(),
				request.getAccountNumber(),
				request.getAccountPassword(),
				request.getAmount(),
				request.getBankServiceType()
		), HttpStatus.OK);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<?> widthdraw(@RequestBody @Valid DepositWithdrawDto.Request request) {
		return new ResponseEntity(transactionService.depositWithdraw(
				request.getUsername(),
				request.getPassword(),
				request.getAccountNumber(),
				request.getAccountPassword(),
				request.getAmount(),
				request.getBankServiceType()

		), HttpStatus.OK);
	}

	@PostMapping("/transfer")
	public ResponseEntity<?> transfer(@RequestBody @Valid TransferDto.Request request) {
		return new ResponseEntity(transactionService.transfer(
				request.getSenderAccountNumber(),
				request.getReceiverAccountNumber(),
				request.getAccountPassword(),
				request.getAmount(),
				request.getBankServiceType()), HttpStatus.OK);
	}

}
