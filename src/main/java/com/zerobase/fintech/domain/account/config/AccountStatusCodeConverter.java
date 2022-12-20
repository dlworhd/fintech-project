package com.zerobase.fintech.domain.account.config;

import com.zerobase.fintech.domain.account.type.AccountErrorCode;
import org.springframework.core.convert.converter.Converter;

public class AccountStatusCodeConverter implements Converter<String, AccountErrorCode> {
	@Override
	public AccountErrorCode convert(String accountErrorCode) {
		return AccountErrorCode.valueOf(accountErrorCode);
	}
}
