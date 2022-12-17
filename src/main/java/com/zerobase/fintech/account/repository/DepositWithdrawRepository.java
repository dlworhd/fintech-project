package com.zerobase.fintech.account.repository;

import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.DepositWithdraw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DepositWithdrawRepository extends JpaRepository<DepositWithdraw, Long> {
	Optional<List<DepositWithdraw>> findByAccount(Account account);
	Optional<List<DepositWithdraw>> findAllByAccountAndTransactionDateBetween(Account account,
	                                                                          LocalDateTime startDt,
	                                                                          LocalDateTime endDt);



}
