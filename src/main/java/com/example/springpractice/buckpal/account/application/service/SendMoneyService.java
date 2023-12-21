package com.example.springpractice.buckpal.account.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.example.springpractice.buckpal.account.application.port.in.SendMoneyCommand;
import com.example.springpractice.buckpal.account.application.port.in.SendMoneyUseCase;
import com.example.springpractice.buckpal.account.application.port.out.AccountLock;
import com.example.springpractice.buckpal.account.application.port.out.LoadAccountPort;
import com.example.springpractice.buckpal.account.application.port.out.UpdateAccountStatePort;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class SendMoneyService implements SendMoneyUseCase {

	private final LoadAccountPort loadAccountPort;

	private final AccountLock accountLock;

	private final UpdateAccountStatePort updateAccountStatePort;

	@Override
	public boolean sendMoney(SendMoneyCommand sendMoneyCommand) {
		// TODO: 12/22/23 비즈니스 규칙 검증
		// TODO: 12/22/23 모델 상태 조작
		// TODO: 12/22/23 출력 값 반환
		return false;
	}
}
