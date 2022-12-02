package com.zerobase.fintech.user.error;

public class EmailExistedException extends RuntimeException{

    public EmailExistedException(String message) {
        super(message);
    }
}


