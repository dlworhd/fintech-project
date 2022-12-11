package com.zerobase.fintech.account.dto;

import com.zerobase.fintech.account.entity.Remittance;
import com.zerobase.fintech.account.entity.Transaction;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemittanceDto {

	private Long amount;
	private Long balance;

	private String senderName;
	private String receiverName;

	private String senderAccountNumber;
	private String receiverAccountNumber;

	public static RemittanceDto fromEntity(Transaction transaction) {
		return RemittanceDto.builder()
				.receiverName(transaction.getRemittance().getReceiverName())
				.receiverAccountNumber(transaction.getRemittance().getReceiverAccountNumber())
				.senderName(transaction.getRemittance().getSenderName())
				.senderAccountNumber(transaction.getRemittance().getSenderAccountNumber())
				.amount(transaction.getAmount())
				.balance(transaction.getBalanceSnapshot())
				.build();
	}
}

