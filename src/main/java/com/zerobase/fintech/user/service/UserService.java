package com.zerobase.fintech.user.service;

import com.zerobase.fintech.user.entity.UserRole;
import com.zerobase.fintech.user.dto.UserDto;
import com.zerobase.fintech.user.entity.Authority;
import com.zerobase.fintech.user.entity.User;
import com.zerobase.fintech.user.repository.UserRepository;
import com.zerobase.fintech.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }


        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        // 비밀번호 알고리즘
        String encPassword = passwordEncoder.encode(userDto.getPassword());
        String encSsn = passwordEncoder.encode(userDto.getSsn());

        User user = User.builder()
                .username(userDto.getUsername())
                .name(userDto.getName())
                .password(encPassword)
                .ssn(encSsn)
                .createdAt(LocalDateTime.now())
                .role(UserRole.ROLE_USER)
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    //유저, 권한 정보를 가져오는 메소드 2개

    //username을 파라미터로 받아서 어떤 username이든 객체를 바로바로 가져올 수 있게 만듦
    public Optional<User> getUserWithAuthorities(String username){
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    //현재 SecurityContext에 저장되어 있는 username에 해당하는 유저 정보와 권한 정보만 받을 수 있는 메소
    public Optional<User> getMyUserWithAuthorities(){
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }

}


