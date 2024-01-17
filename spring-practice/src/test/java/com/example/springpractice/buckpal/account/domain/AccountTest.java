package com.example.springpractice.buckpal.account.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.springpractice.buckpal.helper.AccountHelper;
import com.example.springpractice.buckpal.helper.ActivityHelper;

class AccountTest {

	@Test
	@DisplayName("잔액이 출금액보다 크다면 출금에 성공한다.")
	void test1() {
		// given
		Account.AccountId accountId = new Account.AccountId(1L);

		Account account = AccountHelper.defaultAccount()
			.withAccountId(accountId)
			.withBaselineBalance(Money.of(555L))
			.withActivityWindow(
				new ActivityWindow(
					ActivityHelper.defaultActivity()
						.withTargetAccount(accountId)
						.withMoney(Money.of(999L))
						.build(),
					ActivityHelper.defaultActivity()
						.withTargetAccount(accountId)
						.withMoney(Money.of(1L))
						.build()
				)
			).build();

		// when
		boolean result = account.withdraw(Money.of(555L), new Account.AccountId(2L));

		// then
		assertThat(result).isTrue();
		assertThat(account.getActivityWindow().getActivities()).hasSize(3);
		assertThat(account.calculateBalance().getAmount().longValue()).isEqualTo(1000L);
	}

	@Test
	@DisplayName("잔액이 출금액보다 작다면 출금에 실패하고 예외가 발생한다.")
	void test2() {
		// given
		Account.AccountId accountId = new Account.AccountId(1L);

		Account account = AccountHelper.defaultAccount()
			.withAccountId(accountId)
			.withBaselineBalance(Money.of(555L))
			.withActivityWindow(
				new ActivityWindow(
					ActivityHelper.defaultActivity()
						.withTargetAccount(accountId)
						.withMoney(Money.of(999L))
						.build(),
					ActivityHelper.defaultActivity()
						.withTargetAccount(accountId)
						.withMoney(Money.of(1L))
						.build()
				)
			).build();

		// when, then
		assertThatThrownBy(() -> account.withdraw(Money.of(2000L), new Account.AccountId(2L)))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
