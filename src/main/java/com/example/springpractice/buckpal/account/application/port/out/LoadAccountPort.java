package com.example.springpractice.buckpal.account.application.port.out;

import java.time.LocalDateTime;

import com.example.springpractice.buckpal.account.domain.Account;

public interface LoadAccountPort {

	Account loadAccount(Account.AccountId accountId, LocalDateTime baselineDate);
}
