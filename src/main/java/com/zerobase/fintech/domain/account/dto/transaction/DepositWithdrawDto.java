package com.zerobase.fintech.domain.account.dto.transaction;


import com.zerobase.fintech.domain.account.type.BankServiceType;
import lombok.*;

import javax.validation.constraints.*;

public class DepositWithdrawDto {

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {

		@NotBlank(message = "아이디는 필수 항목입니다.")
		public String username;

		@NotBlank(message = "비밀번호는 필수 항목입니다.")
		public String password;

		@Size(min = 10, max = 10)
		@NotBlank(message = "계좌번호는 필수 항목입니다.")
		public String accountNumber;

		@NotBlank(message = "계좌 비밀번호는 필수 항목입니다.")
		public String accountPassword;

		@Min(10)
		@Max(1000_000_000)
		@NotNull(message = "금액은 필수 항목입니다.")
		public Long amount;

		@NotNull(message = "거래 타입을 입력해주세요. 1.DEPOSIT, 2.WITHDRAW, 3.TRANSFER")
		public BankServiceType bankServiceType;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Response {

		public Long amount;
		public Long balance;

		public static DepositWithdrawDto.Response depositWithdrawFrom(ResultDto resultDto) {
			return DepositWithdrawDto.Response.builder()
					.balance(resultDto.getBalanceSnapshot())
					.amount(resultDto.getAmount())
					.build();
		}
	}
}

