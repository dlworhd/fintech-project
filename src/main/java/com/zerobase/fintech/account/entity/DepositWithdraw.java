package com.zerobase.fintech.account.entity;

import com.zerobase.fintech.account.type.BankServiceType;
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
public class DepositWithdraw {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "account")
	private Account account;

	@OneToOne
	@JoinColumn(name = "transfer_id")
	private Transfer transfer;

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
