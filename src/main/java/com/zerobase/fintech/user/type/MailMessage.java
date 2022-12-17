package com.zerobase.fintech.user.type;


import lombok.Getter;

@Getter
public enum MailMessage {
	EMAIL_AUTH_MESSAGE("인증 메일이 전송되었습니다.");

	private String value;

	MailMessage(String value) {
		this.value = value;
	}
}
