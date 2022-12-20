package com.zerobase.fintech.domain.account.type;

public enum BankServiceType {

	DEPOSIT("DEPOSIT"), WITHDRAWAL("WITHDRAWAL"), TRANSFER("TRANSFER");

	private String description;

	BankServiceType(String description) {
		this.description = description;
	}
}
