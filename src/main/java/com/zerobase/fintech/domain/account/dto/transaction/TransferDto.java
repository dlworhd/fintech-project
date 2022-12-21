package com.zerobase.fintech.domain.account.dto.transaction;

import com.zerobase.fintech.domain.account.type.BankServiceType;
import lombok.*;

import javax.validation.constraints.*;

public class TransferDto {

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {

		@Size(min = 10, max = 10)
		@NotBlank(message = "보내는 사람의 계좌번호는 필수 항목입니다.")
		public String senderAccountNumber;

		@NotBlank(message = "계좌 비밀번호는 필수 항목입니다.")
		public String accountPassword;

		@Size(min = 10, max = 10)
		@NotBlank(message = "받는 사람의 계좌번호는 필수 항목입니다.")
		public String receiverAccountNumber;

		@Min(10)
		@Max(1000_000_000)
		@NotNull(message = "송금 금액은 필수 항목입니다.")
		public Long amount;

		@NotNull(message = "거래 타입을 입력해주세요. 1.DEPOSIT, 2.WITHDRAW, 3.TRANSFER")
		public BankServiceType bankServiceType;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Response {

		private Long amount;
		private Long balance;

		private String senderName;
		private String receiverName;

		private String senderAccountNumber;
		private String receiverAccountNumber;

		public static TransferDto.Response transferFrom(ResultDto resultDto) {
			return Response.builder()
					.amount(resultDto.getAmount())
					.balance(resultDto.getBalanceSnapshot())
					.senderName(resultDto.getSenderName())
					.receiverName(resultDto.getReceiverName())
					.senderAccountNumber(resultDto.getSenderAccountNumber())
					.receiverAccountNumber(resultDto.getReceiverAccountNumber())
					.build();
		}
	}
}

