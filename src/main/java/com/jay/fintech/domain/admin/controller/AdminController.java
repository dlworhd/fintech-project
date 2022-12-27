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

<<<<<<< HEAD:src/main/java/com/jay/fintech/domain/admin/controller/AdminController.java
	@PutMapping("/account")
=======
	@PutMapping("/status/account")
>>>>>>> 7434c76ff6d104fd8f72df8ff326cb29e10c1c8a:src/main/java/com/zerobase/fintech/domain/admin/controller/AdminController.java
	public ResponseEntity<?> accountStatusChange(@RequestBody @Valid AccountStatusDto.Request request) {
		return new ResponseEntity(adminService.accountStatusChange(
				request.getAccountNumber(),
				request.getAccountStatus()), HttpStatus.OK);
	}

<<<<<<< HEAD:src/main/java/com/jay/fintech/domain/admin/controller/AdminController.java
	@PutMapping("/user")
=======
	@PutMapping("/status/user")
>>>>>>> 7434c76ff6d104fd8f72df8ff326cb29e10c1c8a:src/main/java/com/zerobase/fintech/domain/admin/controller/AdminController.java
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
