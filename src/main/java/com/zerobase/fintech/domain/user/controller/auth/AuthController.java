package com.zerobase.fintech.domain.user.controller.auth;

import com.zerobase.fintech.domain.user.dto.Login;
import com.zerobase.fintech.domain.user.service.user.UserService;
import com.zerobase.fintech.global.config.jwt.JwtFilter;
import com.zerobase.fintech.global.config.jwt.TokenProvider;
import com.zerobase.fintech.global.dto.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final UserService userService;

	public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.userService = userService;
	}

	// 1. authenticationToken을 이용해서 Authentication 객체를 생성하기 위해 authenticate()가 실행
	// 2. authenticate()가 실행되면서, loadUSerByUsername()도 실행됨
	@PostMapping("/login")
	public ResponseEntity<TokenDto> authorize(@RequestBody @Valid Login login) {

		// 이메일 인증된 계정 Check
		userService.emailCheck(login.getUsername());

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

		// 1. 만들어진 토큰을 가지고 authentication 토큰을 만들어서 Authentication 객체를 생성
		// 2. Authentication을 Security Context에 등록
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 해당 유저에게 인증 토큰을 만들어서 주는 과정
		String jwt = tokenProvider.createToken(authentication);
		HttpHeaders httpHeaders = new HttpHeaders();

		// 1. JWT 토큰을 Response Header에 넣어줌
		// 2. TokenDto를 이용해서 Response Body에도 넣어서 리턴
		httpHeaders.add(JwtFilter.AUTHRIZATION_HEADER, "Bearer " + jwt);

		return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
	}
}
