package com.zerobase.fintech.config;

import com.zerobase.fintech.user.entity.UserStatus;
import org.springframework.core.convert.converter.Converter;

public class UserStatusCodeConverter implements Converter<String, UserStatus> {

	@Override
	public UserStatus convert(String userStatusCode) {
		return UserStatus.valueOf(userStatusCode);
	}
}


