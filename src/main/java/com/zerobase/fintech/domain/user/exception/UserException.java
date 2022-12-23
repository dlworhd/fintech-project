package com.zerobase.fintech.domain.user.exception;

import com.zerobase.fintech.domain.user.type.UserErrorCode;

public class UserException extends RuntimeException{

    private UserErrorCode userErrorCode;

    public UserException(UserErrorCode userErrorCode) {
        super(userErrorCode.getDescription());
        this.userErrorCode = userErrorCode;
    }
}
