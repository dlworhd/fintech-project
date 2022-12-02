package com.zerobase.fintech.user.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {
        @NotEmpty(message = "이메일은 필수항목입니다.")
        @Email
        private String email;

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
        @NotEmpty(message = "비밀번호는 필수항목입니다.")
        private String password;

        @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
        private String password2;

        @Size(min = 2, max = 25)
        @NotEmpty(message = "이름은 필수항목입니다.")
        private String userName;

        @Size(min = 6, max = 6)
        @NotEmpty(message = "생년월일은 필수 항목입니다.")
        private String ssn1;

        @Size(min = 7, max = 7)
        @NotEmpty(message = "주민등록번호 뒷자리는 필수 항목입니다.")
        private String ssn2;

        public static class Response{
                private String email;
                private String userName;
                private LocalDateTime createdAt;

//                public static Response from(){
//
//                }
        }

}
