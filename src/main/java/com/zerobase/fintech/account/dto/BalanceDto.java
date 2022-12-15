package com.zerobase.fintech.account.dto;

import com.zerobase.fintech.account.entity.Account;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BalanceDto {
	private Long balance;
	private String accountNumber;
	private LocalDateTime nowDate;

	public static BalanceDto fromEntity(Account account){
		return BalanceDto.builder()
				.balance(account.getBalance())
				.accountNumber(account.getAccountNumber())
				.build();
	}
}
