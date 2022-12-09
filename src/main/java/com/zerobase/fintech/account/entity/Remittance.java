package com.zerobase.fintech.account.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Remittance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	private Transaction transaction;

	private String senderName;
	private String receiverName;

	private String senderAccountNumber;
	private String receiverAccountNumber;

}
