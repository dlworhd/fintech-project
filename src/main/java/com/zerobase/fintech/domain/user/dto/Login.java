package com.zerobase.fintech.domain.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Login {

	@NotBlank(message = "아이디는 필수 항목입니다.")
	private String username;

	@NotBlank(message = "비밀번호는 필수 항목입니다.")
	private String password;
}
