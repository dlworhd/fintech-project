package com.zerobase.fintech.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Login {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
