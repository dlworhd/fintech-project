package com.zerobase.fintech.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {
        private String email;
        private String password;
        private String password2;
        private String userName;
        private String ssn1;
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
