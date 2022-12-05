package com.zerobase.fintech.account.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountErrorCode {

    USER_NOT_FOUND("사용자가 없습니다."),
    ACCOUNT_NOT_FOUND("계좌가 없습니다."),
    WRONG_PASSWORD("비밀번호가 틀렸습니다."),
    WRONG_ACCOUNT_PASSWORD("계좌 비밀번호가 틀렸습니다.");

    private final String description;
}

