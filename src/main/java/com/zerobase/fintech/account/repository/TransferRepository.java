package com.zerobase.fintech.account.repository;

import com.zerobase.fintech.account.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
