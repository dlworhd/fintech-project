package com.jay.fintech.domain.user.controller.user;

import com.jay.fintech.domain.user.dto.Register;
import com.jay.fintech.domain.user.entity.User;
import com.jay.fintech.domain.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@GetMapping("/email-auth")
	public ResponseEntity<?> auth(String id) {
		return new ResponseEntity<>(userService.idCheck(UUID.fromString(id)), HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid Register register) {
		userService.register(register);
		return ResponseEntity.ok("이메일 인증을 확인해주세요.");
	}

	@GetMapping("/info")
	public ResponseEntity<User> getMyUserInfoWithAuthorities() {
		return new ResponseEntity(userService.getMyUserWithAuthorities().get(), HttpStatus.OK);
	}

	@GetMapping("/info/{username}")
	public ResponseEntity<User> getMyUserInfo(@PathVariable @Valid String username) {
		return new ResponseEntity(userService.getUserWithAuthorities(username).get(), HttpStatus.OK);
	}
}
