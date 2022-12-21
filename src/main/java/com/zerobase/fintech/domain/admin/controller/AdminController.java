package com.zerobase.fintech.domain.admin.controller;

import com.zerobase.fintech.domain.admin.dto.AccountStatusDto;
import com.zerobase.fintech.domain.admin.dto.UserStatusDto;
import com.zerobase.fintech.domain.admin.service.AdminService;
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

	@PostMapping("/status/account")
	public ResponseEntity<?> accountStatusChange(@RequestBody @Valid AccountStatusDto.Request request) {
		return new ResponseEntity(adminService.accountStatusChange(
				request.getAccountNumber(),
				request.getAccountStatus()), HttpStatus.OK);
	}

	@PostMapping("/status/user")
	public ResponseEntity<?> userStatusChange(@RequestBody @Valid UserStatusDto.Request request) {
		return new ResponseEntity(adminService.userStatusChange(
				request.getUsername(),
				request.getUserStatus()), HttpStatus.OK);
	}

	@GetMapping("/transactions")
	public ResponseEntity<?> recentTransactionHistoriesByAdmin() {
		return new ResponseEntity<>(adminService.recentTransactionHistoriesByAdmin(), HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<?> recentJoinUsersHistories() {
		return new ResponseEntity<>(adminService.recentJoinUsers(), HttpStatus.OK);
	}


}
