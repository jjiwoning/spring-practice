package com.example.springpractice.buckpal.account.application.service;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import com.example.springpractice.buckpal.account.application.port.in.SendMoneyCommand;
import com.example.springpractice.buckpal.account.application.port.in.SendMoneyUseCase;
import com.example.springpractice.buckpal.account.application.port.out.AccountLock;
import com.example.springpractice.buckpal.account.application.port.out.LoadAccountPort;
import com.example.springpractice.buckpal.account.application.port.out.UpdateAccountStatePort;
import com.example.springpractice.buckpal.account.domain.Account;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class SendMoneyService implements SendMoneyUseCase {

	private final LoadAccountPort loadAccountPort;

	private final AccountLock accountLock;

	private final UpdateAccountStatePort updateAccountStatePort;

	@Override
	public boolean sendMoney(SendMoneyCommand sendMoneyCommand) {
		LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

		Account sourceAccount = loadAccountPort.loadAccount(
			sendMoneyCommand.getSourceAccountId(),
			baselineDate);

		Account targetAccount = loadAccountPort.loadAccount(
			sendMoneyCommand.getTargetAccountId(),
			baselineDate);

		Account.AccountId sourceAccountId = sourceAccount.getId()
			.orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));

		Account.AccountId targetAccountId = targetAccount.getId()
			.orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));

		accountLock.lockAccount(sourceAccountId);
		if (!sourceAccount.withdraw(sendMoneyCommand.getMoney(), targetAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			return false;
		}

		accountLock.lockAccount(targetAccountId);
		if (!targetAccount.deposit(sendMoneyCommand.getMoney(), sourceAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			accountLock.releaseAccount(targetAccountId);
			return false;
		}

		updateAccountStatePort.updateActivities(sourceAccount);
		updateAccountStatePort.updateActivities(targetAccount);

		accountLock.releaseAccount(sourceAccountId);
		accountLock.releaseAccount(targetAccountId);
		return true;
	}
}
