package com.zerobase.fintech.domain.account.dto.transaction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zerobase.fintech.domain.account.entity.Transaction;
import com.zerobase.fintech.domain.account.type.BankServiceType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultDto {

	private String accountNumber;
	private BankServiceType bankServiceType;
	private Long amount;
	private Long balanceSnapshot;
	private String receiverName;
	private String receiverAccountNumber;
	private String senderName;
	private String senderAccountNumber;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime transactionDate;

	public static ResultDto fromEntity(Transaction transaction) {
		if (transaction.getTransactionDetail() == null) {
			return ResultDto.builder()
					.accountNumber(transaction.getAccount().getAccountNumber())
					.bankServiceType(transaction.getBankServiceType())
					.balanceSnapshot(transaction.getBalanceSnapshot())
					.transactionDate(transaction.getTransactionDate())
					.amount(transaction.getAmount())
					.build();
		} else {
			return ResultDto.builder()
					.accountNumber(transaction.getAccount().getAccountNumber())
					.bankServiceType(transaction.getBankServiceType())
					.balanceSnapshot(transaction.getBalanceSnapshot())
					.transactionDate(transaction.getTransactionDate())
					.receiverName(transaction.getTransactionDetail().getReceiverName())
					.receiverAccountNumber(transaction.getTransactionDetail().getReceiverAccountNumber())
					.senderName(transaction.getTransactionDetail().getSenderName())
					.senderAccountNumber(transaction.getTransactionDetail().getSenderAccountNumber())
					.amount(transaction.getAmount())
					.build();
		}
	}
}
