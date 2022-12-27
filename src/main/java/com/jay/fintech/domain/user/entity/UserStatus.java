package com.jay.fintech.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {

	ACTIVED("ACTIVED"),
	UNACTIVED("UNACTIVED"),
	BLOCKED("BLOCKED");

	String value;

	UserStatus(String value) {
		this.value = value;
	}
}
