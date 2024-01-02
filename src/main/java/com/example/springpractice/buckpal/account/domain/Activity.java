package com.example.springpractice.buckpal.account.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Activity {

	private ActivityId id;

	private final Account.AccountId ownerAccountId;

	private final Account.AccountId sourceAccountId;

	private final Account.AccountId targetAccountId;

	private final LocalDateTime timestamp;

	private final Money money;

	@Getter
	@AllArgsConstructor
	public static class ActivityId {
		private final Long value;
	}
}
