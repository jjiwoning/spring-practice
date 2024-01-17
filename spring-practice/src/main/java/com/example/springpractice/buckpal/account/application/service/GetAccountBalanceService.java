package com.example.springpractice.buckpal.account.application.service;

import java.time.LocalDateTime;

import com.example.springpractice.buckpal.account.application.port.in.GetAccountBalanceQuery;
import com.example.springpractice.buckpal.account.application.port.out.LoadAccountPort;
import com.example.springpractice.buckpal.account.domain.Account;
import com.example.springpractice.buckpal.account.domain.Money;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetAccountBalanceService implements GetAccountBalanceQuery {

	private final LoadAccountPort loadAccountPort;

	@Override
	public Money getAccountBalance(final Account.AccountId accountId) {
		return loadAccountPort
			.loadAccount(accountId, LocalDateTime.now())
			.calculateBalance();
	}
}
