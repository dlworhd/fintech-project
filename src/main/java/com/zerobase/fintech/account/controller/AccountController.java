package com.zerobase.fintech.account.controller;

import com.zerobase.fintech.account.dto.CreateAccountDto;
import com.zerobase.fintech.account.dto.DeleteAccountDto;
import com.zerobase.fintech.account.dto.InputInfoDto;
import com.zerobase.fintech.account.service.AccountService;
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


	@PostMapping
	public ResponseEntity<CreateAccountDto.Request> createAccount(@RequestBody @Valid CreateAccountDto.Request request) {
		return new ResponseEntity(CreateAccountDto.Response.from(accountService.createAccount(
				request.getUsername(),
				request.getPassword(),
				request.getAccountPassword(),
				request.getInitialBalance())), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<DeleteAccountDto.Response> deleteAccount(@RequestBody @Valid DeleteAccountDto.Request request) {
		return new ResponseEntity(DeleteAccountDto.Response.from(accountService.deleteAccount(
                request.getUsername(),
                request.getPassword(),
                request.getAccountNumber(),
                request.getAccountPassword())), HttpStatus.OK);
	}

	//TODO: 계좌 입출금/송금 내역 조회
	@GetMapping("/{accountNumber}")
	public ResponseEntity<?> getDepositWithdraw(@RequestBody @Valid InputInfoDto inputInfoDto){
		return new ResponseEntity<>(accountService.lookUpDepositWithdraw(inputInfoDto.username,
				inputInfoDto.password,
				inputInfoDto.accountNumber,
				inputInfoDto.accountPassword
		), HttpStatus.OK);
	}



	//TODO: 보유 계좌 조회
	//TODO: 잔액 조회



}
