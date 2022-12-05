package com.zerobase.fintech.account.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;



// inner 클래스를 쓰면 좀 더 명시적이면서 알아 보기 쉬운 장점, 각각을 하나의 DTO라고 보면 됨
public class CreateAccount {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String accountPassword;
        @NotBlank
        @Min(100)
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
