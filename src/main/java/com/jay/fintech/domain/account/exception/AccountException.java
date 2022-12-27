package com.jay.fintech.domain.account.exception;

import com.jay.fintech.domain.account.type.AccountErrorCode;

public class AccountException extends RuntimeException {

	private AccountErrorCode accountErrorCode;

	public AccountException(AccountErrorCode accountErrorCode) {
		super(accountErrorCode.getDescription());
		this.accountErrorCode = accountErrorCode;
	}
}

