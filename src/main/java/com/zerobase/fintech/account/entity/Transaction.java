package com.zerobase.fintech.account.entity;

import com.zerobase.fintech.account.dto.AccountDto;
import com.zerobase.fintech.account.type.TransactionStatus;
import com.zerobase.fintech.account.type.TransactionType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Account account;

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	@Enumerated(EnumType.STRING)
	private TransactionStatus transactionStatus;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime modifiedAt;

	private Long amount;
	private Long balanceSnapshot;
	private LocalDateTime transactionDate;

}
