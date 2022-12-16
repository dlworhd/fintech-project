package com.zerobase.fintech.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Register {

    @Email
    @NotBlank(message = "이메일 형식에 맞게 입력해주세요.")
    private String email;

    @NotBlank(message = "아이디는 6~20자 영문, 숫자를 포함합니다.")
    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$")
    private String username;

    @NotBlank(message = "비밀번호는 8~16자 영문, 숫자를 포함합니다.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}$")
    private String password;

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @NotBlank(message = "주민등록번호 13자리는 필수 항목입니다. 예)991225-1234567")
    @Pattern(regexp = "^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4][0-9]{6}$")
    private String ssn;
}


