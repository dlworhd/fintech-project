package com.zerobase.fintech.account.dto;

import com.zerobase.fintech.account.entity.DepositWithdraw;
import com.zerobase.fintech.account.type.BankServiceType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositWithdrawDto {

	private String accountNumber;
	private BankServiceType bankServiceType;
	private Long amount;
	private Long balanceSnapshot;
	private LocalDateTime transactionDate;

	public static DepositWithdrawDto fromEntity(DepositWithdraw depositWithdraw){
		return DepositWithdrawDto.builder()
				.accountNumber(depositWithdraw.getAccount().getAccountNumber())
				.bankServiceType(depositWithdraw.getBankServiceType())
				.balanceSnapshot(depositWithdraw.getBalanceSnapshot())
				.transactionDate(depositWithdraw.getTransactionDate())
				.amount(depositWithdraw.getAmount())
				.build();
	}
}
