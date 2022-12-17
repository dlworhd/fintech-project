package com.zerobase.fintech.admin.dto;

import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.AccountStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class AccountStatusDto {

	@Getter
	public static class Request {
		@Size(min = 10, max = 10)
		@NotBlank(message = "계좌번호는 필수 항목입니다.")
		public String accountNumber;

		@NotNull(message = "변경할 상태를 직접 입력해주세요 >> REGISTERED, UNREGISTERED, BLOCKED")
		public AccountStatus accountStatus;
	}

	@Getter
	@Setter
	@Builder
	public static class Response {

		public String accountNumber;
		public String accountStatus;

		public static Response fromEntity(Account account) {
			return Response.builder()
					.accountNumber(account.getAccountNumber())
					.accountStatus(account.getAccountStatus().getValue()).
					build();
		}
	}
}
