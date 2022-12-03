package com.zerobase.fintech.account.dto;

import com.zerobase.fintech.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountDto {

    private String initPassword;
    private Long initBalance;

}
