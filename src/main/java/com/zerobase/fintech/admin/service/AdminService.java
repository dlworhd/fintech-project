package com.zerobase.fintech.admin.service;

import com.zerobase.fintech.account.entity.Account;
import com.zerobase.fintech.account.entity.AccountStatus;
import com.zerobase.fintech.account.exception.AccountException;
import com.zerobase.fintech.account.repository.AccountRepository;
import com.zerobase.fintech.account.type.AccountErrorCode;
import com.zerobase.fintech.admin.dto.AccountStatusDto;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

	private AccountRepository accountRepository;
	public AdminService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public AccountStatusDto.Response userStatusChange(String accountNumber, String accountStatus){
		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND));

		if(account.getAccountStatus().getValue().equals(accountStatus)){
			throw new AccountException(AccountErrorCode.OVERLAP_STATUS);
		} else {
			account.setAccountStatus(AccountStatus.valueOf(accountStatus));
			Account savedAccount = accountRepository.save(account);
			return AccountStatusDto.Response.fromEntity(savedAccount);
		}
	}


}
