package com.zerobase.fintech.account.controller;

import com.zerobase.fintech.account.dto.DepositDto;
import com.zerobase.fintech.account.dto.RemittanceInputDto;
import com.zerobase.fintech.account.dto.WithdrawDto;
import com.zerobase.fintech.account.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class TransactionController {

	private final TransactionService transactionService;

	@PostMapping("/user/account/deposit")
	public ResponseEntity<?> deposit(@RequestBody @Valid DepositDto.Request request){
		return new ResponseEntity(transactionService.deposit(
				request.getUsername(),
				request.getPassword(),
				request.getAccountNumber(),
				request.getAccountPassword(),
				request.getAmount()), HttpStatus.OK);
	}

	@PostMapping("/user/account/withdraw")
	public ResponseEntity<?> widthdraw(@RequestBody @Valid WithdrawDto.Request request){
		return new ResponseEntity(transactionService.withdraw(
				request.getUsername(),
				request.getPassword(),
				request.getAccountNumber(),
				request.getAccountPassword(),
				request.getAmount()), HttpStatus.OK);
	}

	@PostMapping("/user/account/send")
	public ResponseEntity<?> send(@RequestBody @Valid RemittanceInputDto.Request request){
		return new ResponseEntity(transactionService.remittance(
				request.getSenderAccountNumber(),
				request.getReceiverAccountNumber(),
				request.getAccountPassword(),
				request.getAmount()), HttpStatus.OK);
	}
}
