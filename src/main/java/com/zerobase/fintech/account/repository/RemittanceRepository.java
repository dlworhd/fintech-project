package com.zerobase.fintech.account.repository;

import com.zerobase.fintech.account.entity.Remittance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemittanceRepository extends JpaRepository<Remittance, Long> {
}
