package com.zerobase.fintech.config;

import com.zerobase.fintech.account.type.AccountErrorCode;
import org.springframework.core.convert.converter.Converter;

public class AccountStatusCodeConverter implements Converter<String, AccountErrorCode> {
	@Override
	public AccountErrorCode convert(String accountErrorCode) {
		return AccountErrorCode.valueOf(accountErrorCode);
	}
}
