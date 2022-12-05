package com.zerobase.fintech.account.entity;

import com.zerobase.fintech.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class) // 매번 시간 데이터를 입력해야 하는 경우에 Audit(감시)을 이용하면 자동으로 시간 매핑하여 테이블에 주입
public class Account {

    @Id
    private String accountNumber;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private Long balance;
    private String password;

    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
