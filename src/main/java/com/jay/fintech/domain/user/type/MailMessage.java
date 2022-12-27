package com.jay.fintech.domain.user.type;


import lombok.Getter;

import java.util.UUID;

@Getter
public enum MailMessage {
	EMAIL_AUTH_MESSAGE("인증 메일이 전송되었습니다."),
	EMAIL_CONTENT_MESSAGE_FRONT("<p>아래 링크를 클릭하셔서 가입을 완료하세요</p><div><a href=\"http://localhost:8080/user/email-auth?id="),
	EMAIL_CONTENT_MESSAGE_BACK("\">인증</a></div>");
	private String value;

	MailMessage(String value) {
		this.value = value;
	}

	public static String setContentMessage(UUID id) {
		return EMAIL_CONTENT_MESSAGE_FRONT.getValue() + id + EMAIL_CONTENT_MESSAGE_BACK.getValue();
	}
}
