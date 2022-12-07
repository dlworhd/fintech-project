package com.zerobase.fintech.user.exception;

import com.zerobase.fintech.user.type.UserErrorCode;

public class UserException extends RuntimeException{

    private UserErrorCode userErrorCode;

    public UserException(UserErrorCode userErrorCode) {
        super(userErrorCode.getDescription());
        this.userErrorCode = userErrorCode;
    }
}
