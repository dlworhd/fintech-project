package com.zerobase.fintech.account.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("사용자가 없습니다."),
    ACCOUNT_NOT_FOUND("찾을 수 있는 계좌가 없습니다. 다시 입력해주세요.");

    private final String description;
}

