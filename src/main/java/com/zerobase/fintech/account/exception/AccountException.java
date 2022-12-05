package com.zerobase.fintech.account.exception;

import com.zerobase.fintech.account.type.AccountErrorCode;

public class AccountException extends RuntimeException{

    private AccountErrorCode accountErrorCode;
    private String errorMessage;

    public AccountException(AccountErrorCode accountErrorCode){
        this.accountErrorCode = accountErrorCode;
        this.errorMessage = accountErrorCode.getDescription();
    }
}
