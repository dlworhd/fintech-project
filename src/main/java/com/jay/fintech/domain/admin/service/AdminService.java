package com.jay.fintech.domain.admin.service;

import com.jay.fintech.domain.account.dto.transaction.ResultDto;
import com.jay.fintech.domain.account.entity.Account;
import com.jay.fintech.domain.account.entity.Transaction;
import com.jay.fintech.domain.account.exception.AccountException;
import com.jay.fintech.domain.account.repository.AccountRepository;
import com.jay.fintech.domain.account.repository.TransactionRepository;
import com.jay.fintech.domain.account.type.AccountErrorCode;
import com.jay.fintech.domain.account.type.AccountStatus;
import com.jay.fintech.domain.admin.dto.AccountStatusDto;
import com.jay.fintech.domain.admin.dto.UserStatusDto;
import com.jay.fintech.domain.user.dto.UserDto;
import com.jay.fintech.domain.user.entity.User;
import com.jay.fintech.domain.user.entity.UserStatus;
import com.jay.fintech.domain.user.exception.UserException;
import com.jay.fintech.domain.user.repository.UserRepository;
import com.jay.fintech.domain.user.type.UserErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final TransactionRepository transactionRepository;

	public AccountStatusDto.Response accountStatusChange(String accountNumber, AccountStatus accountStatus) {
		Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND));

		if (account.getAccountStatus().equals(accountStatus)) {
			throw new AccountException(AccountErrorCode.OVERLAP_STATUS);
		} else {
			account.setAccountStatus(accountStatus);
			account.setModifiedAt(LocalDateTime.now());
			Account savedAccount = accountRepository.save(account);
			return AccountStatusDto.Response.fromEntity(savedAccount);
		}
	}

	public UserStatusDto.Response userStatusChange(String username, UserStatus userStatus) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		if (user.getUserStatus().getValue().equals(userStatus)) {
			throw new UserException(UserErrorCode.OVERLAP_STATUS);
		} else {
			user.setUserStatus(userStatus);
			user.setModifiedAt(LocalDateTime.now());
			return UserStatusDto.Response.fromEntity(userRepository.save(user));
		}
	}


	@Cacheable("RecentTransactions")
	public List<ResultDto> recentTransactionHistoriesByAdmin() {
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<Transaction> list = transactionRepository.findAllByOrderByTransactionDateDesc(pageRequest);
		List<ResultDto> dtoList = new ArrayList<>();
		for (Transaction transaction : list) {
			ResultDto resultDto = ResultDto.fromEntity(transaction);
			dtoList.add(resultDto);
		}
		return dtoList;
	}

	@Cacheable("RecentJoinUsers")
	public List<UserDto> recentJoinUsers() {
		PageRequest pageRequest = PageRequest.of(0, 20);
		Page<User> list = userRepository.findAllByOrderByCreatedAtDesc(pageRequest);
		List<UserDto> dtoList = new ArrayList<>();
		for (User user : list) {
			UserDto userDto = UserDto.fromEntity(user);
			dtoList.add(userDto);
		}

		return dtoList;
	}

}
