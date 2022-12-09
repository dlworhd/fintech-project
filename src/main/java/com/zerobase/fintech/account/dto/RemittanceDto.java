package com.zerobase.fintech.account.dto;

import com.zerobase.fintech.account.entity.Remittance;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemittanceDto {

	private Long transactionId;
	private String senderName;
	private String receiverName;

	private String senderAccountNumber;
	private String receiverAccountNumber;

	public static RemittanceDto fromEntity(Remittance remittance) {
		return RemittanceDto.builder()
				.receiverName(remittance.getReceiverName())
				.receiverAccountNumber(remittance.getReceiverAccountNumber())
				.senderName(remittance.getSenderName())
				.senderAccountNumber(remittance.getSenderAccountNumber())
				.build();
	}
}

