package com.zerobase.fintech.account.type;

public enum BankServiceType {

	DEPOSIT("입금"), WITHDRAWAL("출금"), TRANSFER("송금");

	private String description;

	BankServiceType(String description) {
		this.description = description;
	}
}
