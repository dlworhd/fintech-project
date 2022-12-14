package com.zerobase.fintech.account.controller;

import com.zerobase.fintech.account.dto.CreateAccountDto;
import com.zerobase.fintech.account.dto.DeleteAccountDto;
import com.zerobase.fintech.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AccountController {

	private final AccountService accountService;


	@PostMapping("/account/create")
	public ResponseEntity<CreateAccountDto.Request> createAccount(@RequestBody @Valid CreateAccountDto.Request request) {
		return new ResponseEntity(CreateAccountDto.Response.from(accountService.createAccount(
				request.getUsername(),
				request.getPassword(),
				request.getAccountPassword(),
				request.getInitialBalance())), HttpStatus.OK);
	}

	@DeleteMapping("/account/delete")
	public ResponseEntity<DeleteAccountDto.Response> deleteAccount(@RequestBody @Valid DeleteAccountDto.Request request) {
		return new ResponseEntity(DeleteAccountDto.Response.from(accountService.deleteAccount(
                request.getUsername(),
                request.getPassword(),
                request.getAccountNumber(),
                request.getAccountPassword())), HttpStatus.OK);
	}


}
