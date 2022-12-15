package com.zerobase.fintech.admin.service;

import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.AccountStatus;
import com.zerobase.fintech.account.exception.AccountException;
import com.zerobase.fintech.account.repository.AccountRepository;
import com.zerobase.fintech.account.type.AccountErrorCode;
import com.zerobase.fintech.admin.dto.AccountStatusDto;
import com.zerobase.fintech.admin.dto.UserStatusDto;
import com.zerobase.fintech.user.entity.User;
import com.zerobase.fintech.user.entity.UserStatus;
import com.zerobase.fintech.user.exception.UserException;
import com.zerobase.fintech.user.repository.UserRepository;
import com.zerobase.fintech.user.type.UserErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

	private final AccountRepository accountRepository;
	private final UserRepository userRepository;

	public AccountStatusDto.Response accountStatusChange(String accountNumber, String accountStatus){
		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND));

		if(account.getAccountStatus().getValue().equals(accountStatus)){
			throw new AccountException(AccountErrorCode.OVERLAP_STATUS);
		} else {
			account.setAccountStatus(AccountStatus.valueOf(accountStatus));
			account.setModifiedAt(LocalDateTime.now());
			Account savedAccount = accountRepository.save(account);
			return AccountStatusDto.Response.fromEntity(savedAccount);
		}
	}

	public UserStatusDto.Response userStatusChange(String username, String userStatus){
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		if(user.getUserStatus().getValue().equals(userStatus)){
			throw new UserException(UserErrorCode.OVERLAP_STATUS);
		} else {
			user.setUserStatus(UserStatus.valueOf(userStatus));
			user.setModifiedAt(LocalDateTime.now());
			return UserStatusDto.Response.fromEntity(userRepository.save(user));
		}
	}
}
