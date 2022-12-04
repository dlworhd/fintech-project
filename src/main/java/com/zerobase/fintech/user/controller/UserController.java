package com.zerobase.fintech.user.controller;

import com.zerobase.fintech.user.dto.Register;
import com.zerobase.fintech.user.entity.User;
import com.zerobase.fintech.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Validated @RequestBody Register register) {
        userService.register(register);
        return new ResponseEntity("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return new ResponseEntity(userService.getMyUserWithAuthorities().get(), HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getMyUserInfo(@PathVariable String username) {
        return new ResponseEntity(userService.getUserWithAuthorities(username).get(), HttpStatus.OK);
    }
}
