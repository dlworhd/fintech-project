package com.zerobase.fintech.domain.account.repository;

import com.zerobase.fintech.domain.account.entity.Account;
import com.zerobase.fintech.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

	Optional<Account> findFirstByOrderByAccountNumberDesc();
	Optional<Account> findByAccountNumber(String accountNumber);
	Optional<List<Account>> findAllByUser(User user);
}
