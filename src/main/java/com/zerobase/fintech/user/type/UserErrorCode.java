package com.zerobase.fintech.user.type;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {

    USER_NOT_FOUND("유저를 찾을 수 없습니다."),
    DUPLICATED_USER("이미 가입되어 있는 유저입니다."),
    WRONG_USER_PASSWORD("잘못된 비밀번호입니다.");

    private final String description;
}
