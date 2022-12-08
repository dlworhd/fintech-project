package com.zerobase.fintech.account.dto;

import com.zerobase.fintech.account.entity.Transaction;
import com.zerobase.fintech.account.type.TransactionStatus;
import com.zerobase.fintech.account.type.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {

	private String accountNumber;
	private TransactionType transactionType;
	private TransactionStatus transactionStatus;
	private Long amount;
	private Long balanceSnapshot;
	private LocalDateTime transactionDate;

	public static TransactionDto fromEntity(Transaction transaction){
		return TransactionDto.builder()
				.accountNumber(transaction.getAccount().getAccountNumber())
				.transactionType(transaction.getTransactionType())
				.transactionStatus(transaction.getTransactionStatus())
				.transactionStatus(transaction.getTransactionStatus())
				.balanceSnapshot(transaction.getBalanceSnapshot())
				.amount(transaction.getAmount())
				.build();
	}
}
