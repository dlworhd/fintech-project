package com.jay.fintech.domain.account.dto.account;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.jay.fintech.domain.account.entity.Account;
import com.jay.fintech.domain.account.type.AccountStatus;
import com.jay.fintech.domain.user.entity.User;
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
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime registeredAt;
	@NotNull
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
