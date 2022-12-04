package com.zerobase.fintech.user.service;

import com.zerobase.fintech.user.entity.User;
import com.zerobase.fintech.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@Component("userDetailsService")
public class CustomUserDetailService implements UserDetailsService {
    public CustomUserDetailService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;


    //username을 가지고 DB에서 정보를 가져옴
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneWithAuthoritiesByUsername(username).map(user -> createUser(username, user)).orElseThrow(()
                -> new UsernameNotFoundException(username + " -> DB에서 찾을 수 없습니다."));
    }


    // 로그인시에 DB에서 유저 정보와 권한 정보를 가져오게 되고, 해당 정보를 기반으로 userdetails.User 객체를 생성해서 리턴
    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

}
