package com.zerobase.fintech.account.type;

public enum TransactionType {

	DEPOSIT("입금"), WITHDRAWAL("출금"), REMITTANCE("송금");

	private String description;

	TransactionType(String description) {
		this.description = description;
	}
}
