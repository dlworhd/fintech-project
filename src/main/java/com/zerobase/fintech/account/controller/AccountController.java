//package com.zerobase.fintech.account.controller;
//
//import com.zerobase.fintech.account.dto.CreateAccount;
//import com.zerobase.fintech.account.service.AccountService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//public class AccountController {
//
//
//    private final AccountService accountService;
//
//    @PostMapping("/account")
//    public CreateAccount.Response createAccount(@RequestBody CreateAccount.Request request){ // 토큰도 같이 들어와야 될 듯(User의 정보 -> 그래야 DB에 User 정보와 함께 들어감)
//        accountService.createAccount(request);
//        return
//
//    }
//}
