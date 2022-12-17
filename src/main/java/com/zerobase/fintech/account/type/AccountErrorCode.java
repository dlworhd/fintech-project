package com.zerobase.fintech.account.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountErrorCode {

    USER_NOT_FOUND("사용자가 없습니다."),
    ACCOUNT_NOT_FOUND("계좌가 유효하지 않습니다."),
    RECEIVER_ACCOUNT_NOT_FOUND("받는 사람의 계좌번호가 유효하지 않습니다."),
    WRONG_PASSWORD("비밀번호가 틀렸습니다."),
    WRONG_ACCOUNT_PASSWORD("계좌 비밀번호가 틀렸습니다."),
    ALREADY_UNREGISTERED_ACCOUNT("이미 해지된 계좌입니다."),
    UNREGISTERED_ACCOUNT("해지된 계좌입니다."),
    INFO_NOT_MATCH("아이디가 일치하지 않습니다."),
    NOT_ENOUGH_BALANCE("잔액이 부족합니다."),
    OVERLAP_STATUS("이미 계좌의 상태는 해당 코드와 같습니다."),
    TRANSACTION_NOT_FOUND("거래 내역을 조회할 수 없습니다.");


    private final String description;

}

