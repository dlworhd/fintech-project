package com.zerobase.fintech.user.service;

import com.zerobase.fintech.user.entity.User;
import lombok.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class MailComponent {

	private final JavaMailSender javaMailSender;

	public boolean sendMail(String mail, String subject, String text) {
		boolean result = false;

		//HTML로 메세지 보내는 로직
		MimeMessagePreparator msg = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelper.setTo(mail);
				mimeMessageHelper.setSubject(subject);
				mimeMessageHelper.setText(text, true);
			}
		};
		try {
			javaMailSender.send(msg);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}
}
