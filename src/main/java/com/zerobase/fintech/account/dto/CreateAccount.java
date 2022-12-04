package com.zerobase.fintech.account.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// inner 클래스를 쓰면 좀 더 명시적이면서 알아 보기 쉬운 장점, 각각을 하나의 DTO라고 보면 됨
public class CreateAccount {

    @Getter
    @Setter
    public static class Request {

        @NotNull
        private String username;

        @NotNull
        private String password;

        @NotNull
        private String accountPassword;

        @NotNull
        @Min(100)
        private Long initialBalance;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long userId;
        private Long accountNumber;
        private LocalDateTime registeredAt;

    }
}
