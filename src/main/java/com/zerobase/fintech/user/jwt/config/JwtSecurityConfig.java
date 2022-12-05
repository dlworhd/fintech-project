package com.zerobase.fintech.user.jwt.config;


import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 1. TokenProvider와 JwtFilter를 SecurityConfig에 적용할 때 사용할 클래스
// 2. JWT Filter를 통해서 Security 로직에 필터를 등록한다.
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenProvider tokenProvider;
    public JwtSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtFilter customFilter = new JwtFilter(tokenProvider);

        // Security 로직에 필터를 등록하는 과정
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
