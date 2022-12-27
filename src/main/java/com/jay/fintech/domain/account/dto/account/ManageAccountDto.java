package com.jay.fintech.domain.account.dto.account;

import com.jay.fintech.domain.account.entity.Account;
import com.jay.fintech.domain.account.type.AccountStatus;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ManageAccountDto {

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CreateRequest {

		@NotBlank(message = "아이디는 필수 항목입니다.")
		private String username;

		@NotBlank(message = "비밀번호는 필수 항목입니다.")
		private String password;

		@Size(min = 4, max = 4)
		@NotBlank(message = "비밀번호 4자리를 입력해주세요.")
		private String accountPassword;

		@Min(100)
		@NotNull(message = "계좌 초기 금액 100원 이상 입력해주세요.")
		private Long initialBalance;

	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class CreateResponse {

		private String username;
		private String accountNumber;
		private LocalDateTime registeredAt;

		public static ManageAccountDto.CreateResponse from(AccountDto accountDto) {
			return ManageAccountDto.CreateResponse.builder()
					.username(accountDto.getUsername())
					.accountNumber(accountDto.getAccountNumber())
					.registeredAt(accountDto.getRegisteredAt())
					.build();
		}
	}

	@Getter
	@Setter
	public static class DeleteRequest {

		@NotBlank
		private String username;

		@NotBlank
		private String password;

		@Size(min = 10, max = 10)
		private String accountNumber;

		@Size(min = 4, max = 4)
		@NotBlank(message = "비밀번호 4자리를 입력해주세요.")
		private String accountPassword;

	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class DeleteResponse {

		private String username;
		private String accountNumber;
		private AccountStatus accountStatus;
		private LocalDateTime unRegisteredAt;

		public static ManageAccountDto.DeleteResponse from(AccountDto accountDto) {

			return ManageAccountDto.DeleteResponse.builder()
					.username(accountDto.getUsername())
					.accountNumber(accountDto.getAccountNumber())
					.accountStatus(accountDto.getAccountStatus())
					.unRegisteredAt(accountDto.getUnRegisteredAt())
					.build();
		}
	}


	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class BalanceResponse {
		private Long balance;
		private String accountNumber;
		private LocalDateTime nowDate;

		public static BalanceResponse fromEntity(Account account) {
			return BalanceResponse.builder()
					.balance(account.getBalance())
					.accountNumber(account.getAccountNumber())
					.build();
		}
	}



	@Getter
	@Setter
	@AllArgsConstructor
	public static class PeriodRequest {
		public String accountNumber;
		public String accountPassword;
		public LocalDate startDt;
		public LocalDate endDt;
	}

}
