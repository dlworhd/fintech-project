package com.zerobase.fintech.account.controller;

import com.zerobase.fintech.account.dto.CreateAccount;
import com.zerobase.fintech.account.dto.DeleteAccount;
import com.zerobase.fintech.account.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/account/create")
    public ResponseEntity<CreateAccount.Request> createAccount(@RequestBody @Valid CreateAccount.Request request) {
        return new ResponseEntity(CreateAccount.Response.from(accountService.createAccount(request)), HttpStatus.OK);
    }

    @DeleteMapping("/account/delete")
    public ResponseEntity<DeleteAccount.Response> deleteAccount(@RequestBody @Valid DeleteAccount.Request request) {
        return new ResponseEntity(DeleteAccount.Response.from(accountService.deleteAccount(request)),HttpStatus.OK);
    }
}
