package com.zerobase.fintech.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

        @NotEmpty(message = "아이디 필수 항목입니다.")
        private String username;

        @NotEmpty(message = "이름은 필수 항목입니다.")
        private String name;

        @NotEmpty(message = "비밀번호는 필수 항목입니다.")
        private String password;

        @Size(min = 13, max = 13)
        @NotEmpty(message = "생년월일은 필수 항목입니다.")
        private String ssn;
        }


