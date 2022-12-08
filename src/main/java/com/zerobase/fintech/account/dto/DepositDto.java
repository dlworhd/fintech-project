package com.zerobase.fintech.account.dto;

import lombok.*;

import javax.validation.constraints.*;

public class DepositDto {

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
		@NotNull(message = "입금 금액은 필수 항목입니다.")
		public Long amount;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Response{

		public Long amount;
		public Long balance;

		public static Response from(TransactionDto transactionDto){
			return Response.builder()
					.amount(transactionDto.getAmount())
					.balance(transactionDto.getBalanceSnapshot())
					.build();
		}
	}
}
