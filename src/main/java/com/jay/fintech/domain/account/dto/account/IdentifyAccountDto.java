package com.jay.fintech.domain.account.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IdentifyAccountDto {
	@NotBlank(message = "아이디는 필수 항목입니다.")
	public String username;

	@NotBlank(message = "비밀번호는 필수 항목입니다.")
	public String password;

	@Size(min = 10, max = 10)
	@NotBlank(message = "계좌번호는 필수 항목입니다.")
	public String accountNumber;

	@NotBlank(message = "계좌 비밀번호는 필수 항목입니다.")
	public String accountPassword;
}
