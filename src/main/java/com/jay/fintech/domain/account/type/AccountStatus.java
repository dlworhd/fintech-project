package com.jay.fintech.domain.account.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountStatus {

    REGISTERED("REGISTERED"),
    UNREGISTERED("UNREGISTERED"),
    BLOCKED("BLOCKED");

    private final String value;


}
