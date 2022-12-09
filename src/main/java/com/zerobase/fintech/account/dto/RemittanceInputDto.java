package com.zerobase.fintech.account.dto;

import lombok.*;

import javax.validation.constraints.*;

public class RemittanceInputDto {

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
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Response{

		public Long amount;
		public Long balance;
		public String senderName;
		public String receiverName;


		public static Response from(RemittanceDto remittanceDto){
			return Response.builder()
					.senderName(remittanceDto.getSenderName())
					.receiverName(remittanceDto.getReceiverName())
					.amount(remittanceDto.getAmount())
					.balance(remittanceDto.getBalance())
					.build();
		}
	}
}
