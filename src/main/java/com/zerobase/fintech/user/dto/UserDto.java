package com.zerobase.fintech.user.dto;

import com.zerobase.fintech.user.entity.User;
import com.zerobase.fintech.user.entity.UserRole;
import com.zerobase.fintech.user.entity.UserStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	private UUID id;

	private String username;
	private String password;
	private String name;
	private String ssn;

	private String role;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	private String userStatus;

	public static UserDto fromEntity(User user){
		return UserDto.builder().username(user.getUsername())
				.id(user.getId())
				.password(user.getPassword())
				.name(user.getName())
				.ssn(user.getSsn())
				.role(user.getRole().getValue())
				.createdAt(user.getCreatedAt())
				.modifiedAt(user.getModifiedAt())
				.userStatus(user.getUserStatus().getValue())
				.build();
	}
}
