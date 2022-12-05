package com.zerobase.fintech.account.dto;

import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.AccountStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    private String username;
    private String accountNumber;
    private Long balance;
    private AccountStatus accountStatus;

    private LocalDateTime registeredAt;
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
