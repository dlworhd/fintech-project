package com.zerobase.fintech.user.controller;

import com.zerobase.fintech.user.dto.Register;
import com.zerobase.fintech.user.entity.User;
import com.zerobase.fintech.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/user/email-auth")
    public ResponseEntity<?> auth(String id){
        return new ResponseEntity<>(userService.idCheck(UUID.fromString(id)), HttpStatus.OK);
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody @Valid Register register) {
        userService.register(register);
        return ResponseEntity.ok("이메일 인증을 확인해주세요.");
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
