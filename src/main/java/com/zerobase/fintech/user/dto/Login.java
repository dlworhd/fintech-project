package com.zerobase.fintech.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Login {

    private String username;
    private String password;
}
