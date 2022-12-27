package com.jay.fintech.domain.user.service.user;

import com.jay.fintech.domain.user.dto.Register;
import com.jay.fintech.domain.user.dto.UserDto;
import com.jay.fintech.domain.user.entity.User;
import com.jay.fintech.domain.user.entity.UserRole;
import com.jay.fintech.domain.user.entity.UserStatus;
import com.jay.fintech.domain.user.exception.UserException;
import com.jay.fintech.domain.user.repository.UserRepository;
import com.jay.fintech.domain.user.type.MailMessage;
import com.jay.fintech.domain.user.type.UserErrorCode;
import com.jay.fintech.global.entity.Authority;
import com.jay.fintech.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final MailComponent mailComponent;

	public void emailCheck(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		if (user.getUserStatus() == UserStatus.UNACTIVED) {
			throw new UserException(UserErrorCode.NOT_AUTHENTICATED);
		}
	}


	public void register(Register register) {
		if (userRepository.findOneWithAuthoritiesByUsername(register.getUsername()).orElse(null) != null) {
			throw new UserException(UserErrorCode.DUPLICATED_USER);
		}

		Authority authority = Authority.builder()
				.authorityName("ROLE_USER")
				.build();

		// 비밀번호 알고리즘
		String encPassword = passwordEncoder.encode(register.getPassword());
		String encSsn = passwordEncoder.encode(register.getSsn());

		User user = User.builder()
				.username(register.getUsername())
				.email(register.getEmail())
				.name(register.getName())
				.password(encPassword)
				.ssn(encSsn)
				.role(UserRole.USER)
				.authorities(Collections.singleton(authority))
				.userStatus(UserStatus.UNACTIVED)
				.build();

		User savedUser = userRepository.save(user);
		String email = savedUser.getEmail();

		mailComponent.sendMail(email, MailMessage.EMAIL_AUTH_MESSAGE, MailMessage.setContentMessage(user.getId()));

	}


	//username을 파라미터로 받아서 어떤 username이든 객체를 바로바로 가져올 수 있게 만듦
	public Optional<User> getUserWithAuthorities(String username) {
		return userRepository.findOneWithAuthoritiesByUsername(username);
	}


	//현재 SecurityContext에 저장되어 있는 username에 해당하는 유저 정보와 권한 정보만 받을 수 있는 메소드
	public Optional<User> getMyUserWithAuthorities() {
		return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
	}


	public boolean securityPasswordCheck(String username, String password) {
		if (!passwordEncoder.matches(password, userRepository.findByUsername(username).get().getPassword())) {
			throw new UserException(UserErrorCode.WRONG_USER_PASSWORD);
		}
		return true;
	}

	public UserDto.Response idCheck(UUID uuid) {
		User user = userRepository.findById(uuid)
				.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
		user.setCreatedAt(LocalDateTime.now());
		user.setUserStatus(UserStatus.ACTIVED);
		user.setModifiedAt(LocalDateTime.now());
		User savedUser = userRepository.save(user);

		return UserDto.Response.fromEntity(savedUser);
	}

}