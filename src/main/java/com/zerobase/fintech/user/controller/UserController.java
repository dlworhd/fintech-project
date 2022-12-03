package com.zerobase.fintech.user.controller;

import com.zerobase.fintech.user.dto.UserDto;
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
    public ResponseEntity<User> register(@Validated @RequestBody UserDto userDto){
            return new ResponseEntity(userService.register(userDto), HttpStatus.OK);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<User> getMyUserInfo(){
        return new ResponseEntity(userService.getMyUserWithAuthorities().get(), HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<User> getMyUserInfo(@PathVariable String username){
        return new ResponseEntity(userService.getUserWithAuthorities(username).get(), HttpStatus.OK);
    }
}
