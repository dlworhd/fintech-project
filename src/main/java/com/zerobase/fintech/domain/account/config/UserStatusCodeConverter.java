package com.zerobase.fintech.domain.account.config;

import com.zerobase.fintech.domain.user.entity.UserStatus;
import org.springframework.core.convert.converter.Converter;

public class UserStatusCodeConverter implements Converter<String, UserStatus> {

	@Override
	public UserStatus convert(String userStatusCode) {
		return UserStatus.valueOf(userStatusCode);
	}
}


