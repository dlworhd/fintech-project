package com.zerobase.fintech.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor

public class PeriodDto {
	public String accountNumber;
	public String accountPassword;
	public LocalDate startDt;
	public LocalDate endDt;
}
