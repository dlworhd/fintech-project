package com.zerobase.fintech.account.controller;

import com.zerobase.fintech.account.dto.AccountDto;
import com.zerobase.fintech.account.dto.CreateAccount;
import com.zerobase.fintech.account.dto.DeleteAccount;
import com.zerobase.fintech.account.service.AccountService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/account/create")
    public CreateAccount.Response createAccount(@RequestBody CreateAccount.Request request) {

        return CreateAccount.Response.from(accountService.createAccount(
                request.getUsername(),
                request.getPassword(),
                request.getAccountPassword(),
                request.getInitialBalance())
        );
    }
//
//    @DeleteMapping("/account/delete")
//    public DeleteAccount.Response deleteAccount(@RequestBody DeleteAccount.Request request) {
//        accountService.deleteAccount(request);
//        return DeleteAccount.Response.from();
//    }
}
