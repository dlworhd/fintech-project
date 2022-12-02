package com.zerobase.fintech.user.controller;

import com.zerobase.fintech.user.dto.CreateUserDto;
import com.zerobase.fintech.user.error.EmailExistedException;
import com.zerobase.fintech.user.error.DifferentPasswordException;
import com.zerobase.fintech.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @ExceptionHandler({EmailExistedException.class, DifferentPasswordException.class})
    public ResponseEntity handlerLoginException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody CreateUserDto createUserDto){
        if(userService.isDuplicatedEmail(createUserDto.getEmail())){
            return handlerLoginException(new EmailExistedException("중복된 이메일입니다."));
        }

        if (!createUserDto.getPassword().equals(createUserDto.getPassword2())) {
            return handlerLoginException(new DifferentPasswordException("비밀번호 확인을 일치시켜주세요."));
        }

        if(userService.createAccount(createUserDto)){
            return new ResponseEntity("회원가입 성공 !", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }
    }

}
