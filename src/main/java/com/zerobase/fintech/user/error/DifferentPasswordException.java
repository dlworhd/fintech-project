package com.zerobase.fintech.user.error;

public class DifferentPasswordException extends RuntimeException {
    public DifferentPasswordException(String message) {
        super(message);
    }
}
