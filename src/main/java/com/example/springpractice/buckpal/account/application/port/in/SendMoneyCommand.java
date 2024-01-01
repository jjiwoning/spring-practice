package com.example.springpractice.buckpal.account.application.port.in;

import com.example.springpractice.buckpal.account.domain.Account;
import com.example.springpractice.buckpal.account.domain.Money;
import com.example.springpractice.buckpal.common.SelfValidating;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    @NotNull
    private final Account.AccountId sourceAccountId;

    @NotNull
    private final Account.AccountId targetAccountId;

    @NotNull
    private final Money money;

    private SendMoneyCommand(
            final Account.AccountId sourceAccountId,
            final Account.AccountId targetAccountId,
            final Money money
    ) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.validateSelf();
    }

    public static SendMoneyCommand of(
        final Long sourceAccountId,
        final Long targetAccountId,
        final Long money
    ) {
        return new SendMoneyCommand(
            new Account.AccountId(sourceAccountId),
            new Account.AccountId(targetAccountId),
            Money.of(money));
    }
}
