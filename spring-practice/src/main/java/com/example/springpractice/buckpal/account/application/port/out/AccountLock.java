package com.example.springpractice.buckpal.account.application.port.out;

import com.example.springpractice.buckpal.account.domain.Account;

public interface AccountLock {

	void lockAccount(Account.AccountId accountId);

	void releaseAccount(Account.AccountId accountId);

}
