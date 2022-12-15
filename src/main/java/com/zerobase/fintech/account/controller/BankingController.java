package com.zerobase.fintech.account.controller;

import com.zerobase.fintech.account.dto.DepositWithdrawInputDto;
import com.zerobase.fintech.account.dto.TransferInputDto;
import com.zerobase.fintech.account.service.DepositWithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class BankingController {

	private final DepositWithdrawService depositWithdrawService;

	@PostMapping("/user/account/deposit")
	public ResponseEntity<?> deposit(@RequestBody @Valid DepositWithdrawInputDto.Request request){
		return new ResponseEntity(depositWithdrawService.deposit(
				request.getUsername(),
				request.getPassword(),
				request.getAccountNumber(),
				request.getAccountPassword(),
				request.getAmount()), HttpStatus.OK);
	}

	@PostMapping("/user/account/withdraw")
	public ResponseEntity<?> widthdraw(@RequestBody @Valid DepositWithdrawInputDto.Request request){
		return new ResponseEntity(depositWithdrawService.withdraw(
				request.getUsername(),
				request.getPassword(),
				request.getAccountNumber(),
				request.getAccountPassword(),
				request.getAmount()), HttpStatus.OK);
	}

	@PostMapping("/user/account/send")
	public ResponseEntity<?> send(@RequestBody @Valid TransferInputDto.Request request){
		return new ResponseEntity(depositWithdrawService.transfer(
				request.getSenderAccountNumber(),
				request.getReceiverAccountNumber(),
				request.getAccountPassword(),
				request.getAmount()), HttpStatus.OK);
	}
}
