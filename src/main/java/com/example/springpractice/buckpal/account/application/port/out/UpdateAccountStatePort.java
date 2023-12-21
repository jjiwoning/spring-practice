package com.example.springpractice.buckpal.account.application.port.out;

import com.example.springpractice.buckpal.account.domain.Account;

public interface UpdateAccountStatePort {

	void updateActivities(Account account);

}
