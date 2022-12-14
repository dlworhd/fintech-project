package com.zerobase.fintech.account.dto;

import com.zerobase.fintech.account.entity.DepositWithdraw;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferDto {

	private Long amount;
	private Long balance;

	private String senderName;
	private String receiverName;

	private String senderAccountNumber;
	private String receiverAccountNumber;

	public static TransferDto fromEntity(DepositWithdraw depositWithdraw) {
		return TransferDto.builder()
				.receiverName(depositWithdraw.getTransfer().getReceiverName())
				.receiverAccountNumber(depositWithdraw.getTransfer().getReceiverAccountNumber())
				.senderName(depositWithdraw.getTransfer().getSenderName())
				.senderAccountNumber(depositWithdraw.getTransfer().getSenderAccountNumber())
				.amount(depositWithdraw.getAmount())
				.balance(depositWithdraw.getBalanceSnapshot())
				.build();
	}
}

