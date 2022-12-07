package com.zerobase.fintech.account.dto;

import com.zerobase.fintech.account.entity.AccountStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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
        @Size(min = 4, max = 4)
        @NotBlank(message = "비밀번호 4자리를 입력해주세요.")
        private String accountPassword;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        @NotBlank
        private String username;
        @NotBlank
        private String accountNumber;
        @NotNull
        private AccountStatus accountStatus;
        @NotNull
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
