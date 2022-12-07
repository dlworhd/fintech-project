package com.zerobase.fintech.account.dto;

import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.AccountStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    @NotBlank
    private String username;
    @NotBlank
    private String accountNumber;
    @NotNull
    private Long balance;
    @NotNull
    private AccountStatus accountStatus;
    @NotNull
    private LocalDateTime registeredAt;
    @NotNull
    private LocalDateTime unRegisteredAt;

    public static AccountDto fromEntity(Account account) {
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .username(account.getUser().getUsername())
                .balance(account.getBalance())
                .registeredAt(account.getRegisteredAt())
                .unRegisteredAt(account.getUnRegisteredAt())
                .accountStatus(account.getAccountStatus())
                .build();
    }
}
