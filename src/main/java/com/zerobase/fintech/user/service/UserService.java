package com.zerobase.fintech.user.service;

import com.zerobase.fintech.UserRole;
import com.zerobase.fintech.user.domain.User;
import com.zerobase.fintech.user.dto.CreateUserDto;
import com.zerobase.fintech.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 아이디 생성
    public boolean createAccount(CreateUserDto createUserDto) {
        if (isDuplicatedEmail(createUserDto.getEmail())) {
            return false;
        }
        if (!createUserDto.getPassword().equals(createUserDto.getPassword2())) {
            return false;
        }

        // 비밀번호 알고리즘
        String encPassword = passwordEncoder.encode(createUserDto.getPassword());
        String encSsn = passwordEncoder.encode(createUserDto.getSsn1() + createUserDto.getSsn2());

        User user = User.builder()
                .email(createUserDto.getEmail())
                .password(encPassword)
                .ssn(encSsn)
                .userName(createUserDto.getUserName())
                .createdAt(LocalDateTime.now())
                .userType("user").build();

        userRepository.save(user);
        return true;
    }

    // 이메일 중복 확인
    public boolean isDuplicatedEmail(String email) {
        boolean emailDuplicate = userRepository.existsByEmail(email);
        return emailDuplicate;
    }

    // 로그인
    public boolean validationLogin(String email, String password) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        if (!user.isPresent()) {
            return false;
        }
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            return false;
        }
        return true;
    }
}


