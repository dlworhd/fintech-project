package com.zerobase.fintech.domain.admin.dto;

import com.zerobase.fintech.domain.user.entity.User;
import com.zerobase.fintech.domain.user.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UserStatusDto {

	@Getter
	public static class Request {

		@NotBlank(message = "아이디는 필수 항목입니다.")
		public String username;

		@NotNull(message = "변경할 상태를 직접 입력해주세요 >> ACTIVED, BLOCKED")
		public UserStatus userStatus;
	}

	@Getter
	@Setter
	@Builder
	public static class Response {

		public String username;
		public UserStatus userStatus;

		public static Response fromEntity(User user) {
			return Response.builder()
					.username(user.getUsername())
					.userStatus(user.getUserStatus()).
					build();
		}
	}
}
