package com.example.springpractice.buckpal.account.domain;

import java.math.BigInteger;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Money {

	public static Money ZERO = Money.of(0L);

	@NonNull
	private final BigInteger amount;

	private Money(BigInteger amount) {
		if (isNegative(amount)) {
			throw new IllegalArgumentException("Money의 값은 음수일 수 없습니다.");
		}
		this.amount = amount;
	}

	private boolean isNegative(BigInteger amount){
		return amount.compareTo(BigInteger.ZERO) < 0;
	}

	public boolean isPositiveOrZero(){
		return this.amount.compareTo(BigInteger.ZERO) >= 0;
	}

	public boolean isPositive(){
		return this.amount.compareTo(BigInteger.ZERO) > 0;
	}

	public boolean isGreaterThanOrEqualTo(Money money){
		return this.amount.compareTo(money.amount) >= 0;
	}

	public boolean isGreaterThan(Money money){
		return this.amount.compareTo(money.amount) >= 1;
	}

	public static Money of(long value) {
		return new Money(BigInteger.valueOf(value));
	}

	public static Money add(Money money1, Money money2) {
		return new Money(money1.amount.add(money2.amount));
	}

	public static Money subtract(Money money1, Money money2) {
		return new Money(money1.amount.subtract(money2.amount));
	}

	public Money minus(Money otherMoney){
		return new Money(this.amount.subtract(otherMoney.amount));
	}

	public Money plus(Money otherMoney){
		return new Money(this.amount.add(otherMoney.amount));
	}

	public Money negate(){
		return new Money(this.amount.negate());
	}

}
