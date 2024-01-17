package com.example.springpractice.buckpal.account.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.springpractice.buckpal.account.application.port.in.SendMoneyCommand;
import com.example.springpractice.buckpal.account.application.port.out.AccountLock;
import com.example.springpractice.buckpal.account.application.port.out.LoadAccountPort;
import com.example.springpractice.buckpal.account.application.port.out.UpdateAccountStatePort;
import com.example.springpractice.buckpal.account.domain.Account;
import com.example.springpractice.buckpal.account.domain.Money;

@ExtendWith(MockitoExtension.class)
class SendMoneyServiceTest {

	@Mock
	private LoadAccountPort loadAccountPort;

	@Mock
	private AccountLock accountLock;

	@Mock
	private UpdateAccountStatePort updateAccountStatePort;

	@InjectMocks
	private SendMoneyService sendMoneyService;

	@Test
	@DisplayName("입출금에 성공한다.")
	void test1() {
		Account sourceAccount = givenSourceAccount();
		Account targetAccount = givenTargetAccount();

		givenWithdrawalWillSucceed(sourceAccount);
		givenDepositWillSucceed(targetAccount);

		Money money = Money.of(500L);

		SendMoneyCommand command = SendMoneyCommand.of(
			sourceAccount.getId().get().getValue(),
			targetAccount.getId().get().getValue(),
			money.getAmount().longValue());

		boolean success = sendMoneyService.sendMoney(command);

		assertThat(success).isTrue();

		Account.AccountId sourceAccountId = sourceAccount.getId().get();
		Account.AccountId targetAccountId = targetAccount.getId().get();

		then(accountLock).should().lockAccount(eq(sourceAccountId));
		then(sourceAccount).should().withdraw(eq(money), eq(targetAccountId));
		then(accountLock).should().releaseAccount(eq(sourceAccountId));

		then(accountLock).should().lockAccount(eq(targetAccountId));
		then(targetAccount).should().deposit(eq(money), eq(sourceAccountId));
		then(accountLock).should().releaseAccount(eq(targetAccountId));

		thenAccountsHaveBeenUpdated(sourceAccountId, targetAccountId);
	}

	private Account givenTargetAccount(){
		return givenAnAccountWithId(new Account.AccountId(42L));
	}

	private Account givenSourceAccount(){
		return givenAnAccountWithId(new Account.AccountId(41L));
	}

	private Account givenAnAccountWithId(Account.AccountId id) {
		Account account = Mockito.mock(Account.class);
		given(account.getId())
			.willReturn(Optional.of(id));
		given(loadAccountPort.loadAccount(eq(account.getId().get()), any(LocalDateTime.class)))
			.willReturn(account);
		return account;
	}

	private void thenAccountsHaveBeenUpdated(Account.AccountId... accountIds){
		ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
		then(updateAccountStatePort).should(times(accountIds.length))
			.updateActivities(accountCaptor.capture());

		List<Account.AccountId> updatedAccountIds = accountCaptor.getAllValues()
			.stream()
			.map(Account::getId)
			.map(Optional::get)
			.collect(Collectors.toList());

		for(Account.AccountId accountId : accountIds){
			assertThat(updatedAccountIds).contains(accountId);
		}
	}

	private void givenDepositWillSucceed(Account account) {
		given(account.deposit(any(Money.class), any(Account.AccountId.class)))
			.willReturn(true);
	}

	private void givenWithdrawalWillFail(Account account) {
		given(account.withdraw(any(Money.class), any(Account.AccountId.class)))
			.willReturn(false);
	}

	private void givenWithdrawalWillSucceed(Account account) {
		given(account.withdraw(any(Money.class), any(Account.AccountId.class)))
			.willReturn(true);
	}
}
