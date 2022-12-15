package com.zerobase.fintech.admin.dto;

import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class UserStatusDto {

	@Getter
	public static class Request {

		@NotBlank(message = "아이디는 필수 항목입니다.")
		public String username;

		@NotBlank(message = "변경할 상태를 직접 입력해주세요 >> ACTIVED, BLOCKED")
		public String userStatus;
	}

	@Getter
	@Setter
	@Builder
	public static class Response {

		public String username;
		public String userStatus;

		public static Response fromEntity(User user) {
			return Response.builder()
					.username(user.getUsername())
					.userStatus(user.getUserStatus().getValue()).
					build();
		}
	}
}
