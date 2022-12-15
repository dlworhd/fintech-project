package com.zerobase.fintech.account.repository;

import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.DepositWithdraw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepositWithdrawRepository extends JpaRepository<DepositWithdraw, Long> {
	Optional<List<DepositWithdraw>> findByAccount(Account account);

}
