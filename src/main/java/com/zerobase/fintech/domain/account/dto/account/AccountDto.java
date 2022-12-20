package com.zerobase.fintech.domain.account.dto.account;

import com.zerobase.fintech.domain.account.entity.Account;
import com.zerobase.fintech.domain.account.type.AccountStatus;
import com.zerobase.fintech.domain.user.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

	@NotBlank
	private String username;
	@NotBlank
	private String accountNumber;
	@NotNull
	private Long balance;
	@NotNull
	private AccountStatus accountStatus;
	@NotNull
	private LocalDateTime registeredAt;
	@NotNull
	private LocalDateTime unRegisteredAt;

	@NotNull
	private User user;

	public static AccountDto fromEntity(Account account) {
		return AccountDto.builder()
				.username(account.getUser().getUsername())
				.accountNumber(account.getAccountNumber())
				.balance(account.getBalance())
				.accountStatus(account.getAccountStatus())
				.registeredAt(account.getRegisteredAt())
				.unRegisteredAt(account.getUnRegisteredAt())
				.user(account.getUser())
				.build();
	}
}
