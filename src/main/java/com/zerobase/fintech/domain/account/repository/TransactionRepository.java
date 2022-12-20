package com.zerobase.fintech.domain.account.repository;

import com.zerobase.fintech.domain.account.entity.Account;
import com.zerobase.fintech.domain.account.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	Optional<List<Transaction>> findByAccount(Account account);
	Optional<List<Transaction>> findAllByAccountAndTransactionDateBetween(Account account,
	                                                                      LocalDateTime startDt,
	                                                                      LocalDateTime endDt);
}
