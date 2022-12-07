package com.zerobase.fintech.account.exception;

import com.zerobase.fintech.account.type.AccountErrorCode;

public class AccountException extends RuntimeException {

    private AccountErrorCode accountErrorCode;

    public AccountException(AccountErrorCode accountErrorCode) {
        super(accountErrorCode.getDescription());
        this.accountErrorCode = accountErrorCode;
    }
}

