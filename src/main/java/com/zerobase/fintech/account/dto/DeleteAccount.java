package com.zerobase.fintech.account.dto;

import com.zerobase.fintech.account.entity.AccountStatus;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


// inner 클래스를 쓰면 좀 더 명시적이면서 알아 보기 쉬운 장점, 각각을 하나의 DTO라고 보면 됨
public class DeleteAccount {

    @Getter
    @Setter
    public static class Request {

        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String accountNumber;
        @NotBlank
        private String accountPassword;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String username;
        private String accountNumber;
        private AccountStatus accountStatus;
        private LocalDateTime unRegisteredAt;

        public static Response from(AccountDto accountDto){

            return Response.builder()
                    .username(accountDto.getUsername())
                    .accountNumber(accountDto.getAccountNumber())
                    .accountStatus(accountDto.getAccountStatus())
                    .unRegisteredAt(accountDto.getUnRegisteredAt())
                    .build();
        }
    }
}
