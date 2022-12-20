package com.zerobase.fintech.domain.account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new UserStatusCodeConverter());
		registry.addConverter(new AccountStatusCodeConverter());
		registry.addConverter(new BankServiceTypeConverter());
	}
}
