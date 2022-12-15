package com.zerobase.fintech.admin.controller;

import com.zerobase.fintech.admin.dto.UserStatusDto;
import com.zerobase.fintech.admin.service.AdminService;
import com.zerobase.fintech.admin.dto.AccountStatusDto;
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
	public ResponseEntity<?> accountStatusChange(@RequestBody @Valid AccountStatusDto.Request request){
			return new ResponseEntity(adminService.accountStatusChange(
				request.getAccountNumber(),
				request.getAccountStatus()), HttpStatus.OK);
	}

	@PostMapping("/status/user")
	public ResponseEntity<?> userStatusChange(@RequestBody @Valid UserStatusDto.Request request){
		return new ResponseEntity(adminService.userStatusChange(
				request.getUsername(),
				request.getUserStatus()), HttpStatus.OK);
	}

	//TODO: 유저 거래 취소 (이메일을 통한 수취인의 동의 필요 -> 수취인 로그인 후 계좌, 계좌 비밀번호까지 입력을 해야 되는 걸로?)
	//TODO: 거래 내역 확인 (뷰 페이지를 통해서 페이징 처리 )
	@GetMapping("/transactions")
	public ResponseEntity<?> transactions(){
		return new ResponseEntity<>(adminService.getTransactions(), HttpStatus.OK);
	}


	//TODO: 회원 전체 조회




}
