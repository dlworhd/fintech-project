package com.jay.fintech.domain.account.repository;

import com.jay.fintech.domain.account.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {
}
