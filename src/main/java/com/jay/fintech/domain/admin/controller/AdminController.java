package com.jay.fintech.domain.admin.controller;

import com.jay.fintech.domain.admin.dto.AccountStatusDto;
import com.jay.fintech.domain.admin.dto.UserStatusDto;
import com.jay.fintech.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@PutMapping("/status/account")
	public ResponseEntity<?> accountStatusChange(@RequestBody @Valid AccountStatusDto.Request request) {
		return new ResponseEntity(adminService.accountStatusChange(
				request.getAccountNumber(),
				request.getAccountStatus()), HttpStatus.OK);
	}

	@PutMapping("/status/user")
	public ResponseEntity<?> userStatusChange(@RequestBody @Valid UserStatusDto.Request request) {

		return new ResponseEntity(adminService.userStatusChange(
				request.getUsername(),
				request.getUserStatus()), HttpStatus.OK);
	}

	@GetMapping("/histories")
	public ResponseEntity<?> histories() {
		return new ResponseEntity<>(adminService.recentTransactionHistoriesByAdmin(), HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<?> usersHistories() {
		return new ResponseEntity<>(adminService.recentJoinUsers(), HttpStatus.OK);
	}


}
