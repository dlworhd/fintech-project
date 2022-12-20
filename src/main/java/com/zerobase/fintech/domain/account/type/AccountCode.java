package com.zerobase.fintech.domain.account.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountCode {
    INIT_CODE("8200000000");

    private final String value;

}
