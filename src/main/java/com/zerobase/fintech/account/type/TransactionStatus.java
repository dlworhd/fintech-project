package com.zerobase.fintech.account.type;

public enum TransactionStatus {

	SUCCESS("성공"), FAIL("실패");

	TransactionStatus(String description) {
		this.description = description;
	}

	private String description;
}
