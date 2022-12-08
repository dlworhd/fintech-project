package com.zerobase.fintech.account.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class CreateAccountDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        @NotBlank(message = "아이디는 필수 항목입니다.")
        private String username;

        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        private String password;

        @Size(min = 4, max = 4)
        @NotBlank(message = "비밀번호 4자리를 입력해주세요.")
        private String accountPassword;

        @Min(100)
        @NotNull(message = "계좌 초기 금액 100원 이상 입력해주세요.")
        private Long initialBalance;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String username;
        private String accountNumber;
        private LocalDateTime registeredAt;

        public static Response from(AccountDto accountDto){
            return Response.builder()
                    .username(accountDto.getUsername())
                    .accountNumber(accountDto.getAccountNumber())
                    .registeredAt(accountDto.getRegisteredAt())
                    .build();
        }
    }
}
