package com.zerobase.fintech.account.repository;

import com.zerobase.fintech.account.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
