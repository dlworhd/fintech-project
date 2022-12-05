package com.zerobase.fintech.user.exception;

import com.zerobase.fintech.user.type.UserErrorCode;

public class UserException extends RuntimeException{

    private UserErrorCode userErrorCode;
    private String errorMessage;

    public UserException(UserErrorCode userErrorCode) {
        this.userErrorCode = userErrorCode;
        this.errorMessage = userErrorCode.getDescription();
    }
}
