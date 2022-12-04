package com.zerobase.fintech.account.exception;

import com.zerobase.fintech.account.type.ErrorCode;

public class AccountException extends RuntimeException{

    private com.zerobase.fintech.account.type.ErrorCode errorCode;
    private String errorMessage;

    public AccountException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }

}
