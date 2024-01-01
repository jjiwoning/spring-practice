package com.example.springpractice.buckpal.account.application.port.in;

import com.example.springpractice.buckpal.account.domain.Account;
import com.example.springpractice.buckpal.account.domain.Money;

public interface GetAccountBalanceQuery {

	Money getAccountBalance(Account.AccountId accountId);

}
