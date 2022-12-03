package com.zerobase.fintech.account.entity;

import com.zerobase.fintech.account.dto.CreateAccountDto;
import com.zerobase.fintech.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @ManyToOne
    private User user;

    private String accountNumber;
    private Long balance;
    private String password;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;


    public static Account of(CreateAccountDto createAccountDto){
        return Account.builder()
                .user(createAccountDto.getUser())
                .balance(createAccountDto.getInitBalance())
                .password(createAccountDto.getInitPassword())
                .build();
    }
}
