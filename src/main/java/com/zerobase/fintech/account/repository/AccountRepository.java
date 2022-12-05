package com.zerobase.fintech.account.repository;

import com.zerobase.fintech.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findFirstByOrderByAccountNumberDesc();

    Optional<Account> findByAccountNumber(String accountNumber);
}
