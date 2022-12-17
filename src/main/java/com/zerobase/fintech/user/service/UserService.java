package com.zerobase.fintech.user.service;

import com.zerobase.fintech.user.dto.Register;
import com.zerobase.fintech.user.dto.UserDto;
import com.zerobase.fintech.user.entity.UserRole;
import com.zerobase.fintech.user.exception.UserException;
import com.zerobase.fintech.auth.entity.Authority;
import com.zerobase.fintech.user.entity.User;
import com.zerobase.fintech.user.repository.UserRepository;
import com.zerobase.fintech.user.type.MailMessage;
import com.zerobase.fintech.user.type.UserErrorCode;
import com.zerobase.fintech.auth.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.zerobase.fintech.user.entity.UserStatus.ACTIVED;
import static com.zerobase.fintech.user.entity.UserStatus.UNACTIVED;

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

		if (user.getUserStatus() == UNACTIVED) {
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
				.userStatus(UNACTIVED)
				.build();

		User savedUser = userRepository.save(user);
		String email = savedUser.getEmail();

		mailComponent.sendMail(email, MailMessage.EMAIL_AUTH_MESSAGE,MailMessage.setContentMessage(user.getId()));

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
		user.setUserStatus(ACTIVED);
		user.setModifiedAt(LocalDateTime.now());
		User savedUser = userRepository.save(user);

		return UserDto.Response.fromEntity(savedUser);
	}

}