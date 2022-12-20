package com.zerobase.fintech.domain.admin.service;

import com.zerobase.fintech.domain.account.dto.transaction.ResultDto;
import com.zerobase.fintech.domain.account.entity.Account;
import com.zerobase.fintech.domain.account.entity.Transaction;
import com.zerobase.fintech.domain.account.exception.AccountException;
import com.zerobase.fintech.domain.account.repository.AccountRepository;
import com.zerobase.fintech.domain.account.repository.TransactionRepository;
import com.zerobase.fintech.domain.account.type.AccountErrorCode;
import com.zerobase.fintech.domain.account.type.AccountStatus;
import com.zerobase.fintech.domain.admin.dto.AccountStatusDto;
import com.zerobase.fintech.domain.admin.dto.UserStatusDto;
import com.zerobase.fintech.domain.user.dto.UserDto;
import com.zerobase.fintech.domain.user.entity.User;
import com.zerobase.fintech.domain.user.entity.UserStatus;
import com.zerobase.fintech.domain.user.exception.UserException;
import com.zerobase.fintech.domain.user.repository.UserRepository;
import com.zerobase.fintech.domain.user.type.UserErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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
		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND));

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
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		if (user.getUserStatus().getValue().equals(userStatus)) {
			throw new UserException(UserErrorCode.OVERLAP_STATUS);
		} else {
			user.setUserStatus(userStatus);
			user.setModifiedAt(LocalDateTime.now());
			return UserStatusDto.Response.fromEntity(userRepository.save(user));
		}
	}


	@Cacheable(value = "Transaction")
	public List<ResultDto> getTransactions() {
		List<Transaction> list = transactionRepository.findAll();
		List<ResultDto> dtoList = new ArrayList<>();
		for (Transaction transaction : list) {
			ResultDto resultDto = ResultDto.transferFromEntity(transaction);
			dtoList.add(resultDto);
		}

		return dtoList;
	}

	@Cacheable(value = "Users")
	public List<UserDto> getUsers() {
		List<User> list = userRepository.findAll();
		List<UserDto> dtoList = new ArrayList<>();
		for (User user : list) {
			UserDto userDto = UserDto.fromEntity(user);
			dtoList.add(userDto);
		}

		return dtoList;
	}

}
