package com.jay.fintech.domain.account.config;

import com.jay.fintech.domain.account.type.BankServiceType;
import org.springframework.core.convert.converter.Converter;

public class BankServiceTypeConverter implements Converter<String, BankServiceType> {

	@Override
	public BankServiceType convert(String bankServiceType) {
		return BankServiceType.valueOf(bankServiceType);
	}
}



