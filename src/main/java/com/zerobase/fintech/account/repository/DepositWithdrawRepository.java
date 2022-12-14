package com.zerobase.fintech.account.repository;

import com.zerobase.fintech.account.entity.DepositWithdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositWithdrawRepository extends JpaRepository<DepositWithdraw, Long> {
}
