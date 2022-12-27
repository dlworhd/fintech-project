package com.jay.fintech.domain.account.repository;

import com.jay.fintech.domain.account.entity.Account;
import com.jay.fintech.domain.account.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	Optional<List<Transaction>> findAllByAccountAndTransactionDateBetween(Account account,
	                                                                      LocalDateTime startDt,
	                                                                      LocalDateTime endDt);

	Page<Transaction> findAllByOrderByTransactionDateDesc(Pageable pageable);
	Page<Transaction> findAllByAccountOrderByTransactionDateDesc(Account account, Pageable pageable);

}
