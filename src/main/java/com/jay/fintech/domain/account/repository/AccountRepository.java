package com.jay.fintech.domain.account.repository;

import com.jay.fintech.domain.account.entity.Account;
import com.jay.fintech.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

	Optional<Account> findFirstByOrderByAccountNumberDesc();
	Optional<Account> findByAccountNumber(String accountNumber);
	Optional<List<Account>> findAllByUser(User user);
}
