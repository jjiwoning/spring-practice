package com.example.springpractice.buckpal.account.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.springpractice.buckpal.account.application.port.out.LoadAccountPort;
import com.example.springpractice.buckpal.account.application.port.out.UpdateAccountStatePort;
import com.example.springpractice.buckpal.account.domain.Account;
import com.example.springpractice.buckpal.account.domain.Activity;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

	private final AccountRepository accountRepository;
	private final ActivityRepository activityRepository;
	private final AccountMapper accountMapper;

	@Override
	public Account loadAccount(
		final Account.AccountId accountId,
		final LocalDateTime baselineDate
	) {
		AccountJpaEntity account =
			accountRepository.findById(accountId.getValue())
				.orElseThrow(EntityNotFoundException::new);

		List<ActivityJpaEntity> activities =
			activityRepository.findByOwnerSince(
				accountId.getValue(),
				baselineDate);

		Long withdrawalBalance = orZero(activityRepository
			.getWithdrawalBalanceUntil(
				accountId.getValue(),
				baselineDate));

		Long depositBalance = orZero(activityRepository
			.getDepositBalanceUntil(
				accountId.getValue(),
				baselineDate));

		return accountMapper.mapToDomainEntity(
			account,
			activities,
			withdrawalBalance,
			depositBalance);

	}

	private Long orZero(final Long value) {
		return value == null ? 0L : value;
	}

	@Override
	public void updateActivities(final Account account) {
		for (Activity activity : account.getActivityWindow().getActivities()) {
			if (activity.getId() == null) {
				activityRepository.save(accountMapper.mapToJpaEntity(activity));
			}
		}
	}

}
