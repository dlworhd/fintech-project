package com.jay.fintech.domain.account.entity;

import com.jay.fintech.domain.account.type.BankServiceType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "account")
	private Account account;

	@OneToOne
	@JoinColumn(name = "transaction_detail_id")
	private TransactionDetail transactionDetail;

	@Enumerated(EnumType.STRING)
	private BankServiceType bankServiceType;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime modifiedAt;

	private Long amount;
	private Long balanceSnapshot;

	private LocalDateTime transactionDate;

}
