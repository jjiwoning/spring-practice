package com.example.springpractice.buckpal.account.domain;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class Account {

	private final AccountId id;

	private final Money baselineBalance;

	private final ActivityWindow activityWindow;

	public static Account withoutId(
		final Money baselineBalance,
		final ActivityWindow activityWindow
	) {
		return new Account(null, baselineBalance, activityWindow);
	}

	public static Account withId(
		final AccountId accountId,
		final Money baselineBalance,
		final ActivityWindow activityWindow
	) {
		return new Account(accountId, baselineBalance, activityWindow);
	}

	public Optional<AccountId> getId() {
		return Optional.ofNullable(this.id);
	}

	public Money calculateBalance() {
		return Money.add(
			this.baselineBalance,
			this.activityWindow.calculateBalance(this.id));
	}

	public boolean withdraw(
		final Money money,
		final AccountId targetAccountId
	) {
		if (!mayWithdraw(money)) {
			return false;
		}

		Activity withdrawal = new Activity(
			this.id,
			this.id,
			targetAccountId,
			LocalDateTime.now(),
			money);

		this.activityWindow.addActivity(withdrawal);
		return true;
	}

	private boolean mayWithdraw(final Money money) {
		return Money.subtract(
				this.calculateBalance(),
				money)
			.isPositiveOrZero();
	}

	public boolean deposit(
		final Money money,
		final AccountId sourceAccountId
	) {
		Activity deposit = new Activity(
			this.id,
			sourceAccountId,
			this.id,
			LocalDateTime.now(),
			money);
		this.activityWindow.addActivity(deposit);
		return true;
	}

	@AllArgsConstructor
	@Getter
	@EqualsAndHashCode
	public static class AccountId {
		private Long value;
	}
}
