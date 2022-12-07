package com.zerobase.fintech.user.controller;

import com.zerobase.fintech.user.dto.Register;
import com.zerobase.fintech.user.entity.User;
import com.zerobase.fintech.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<User> register(@RequestBody @Valid Register register) {
        return new ResponseEntity(userService.register(register), HttpStatus.OK);
    }

    @GetMapping("/user/info")
    public ResponseEntity<User> getMyUserInfo() {
        return new ResponseEntity(userService.getMyUserWithAuthorities().get(), HttpStatus.OK);
    }

    @GetMapping("/user/info/{username}")
    public ResponseEntity<User> getMyUserInfo(@PathVariable @Valid String username) {
        return new ResponseEntity(userService.getUserWithAuthorities(username).get(), HttpStatus.OK);
    }
}
