package com.zerobase.fintech.account.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountStatus {

    ACCOUNT_REGISTERED("REGISTERED"),
    ACCOUNT_UNREGISTERED("UNREGISTERED");

    private final String value;
}
