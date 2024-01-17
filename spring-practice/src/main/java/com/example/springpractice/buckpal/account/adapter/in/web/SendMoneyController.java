package com.example.springpractice.buckpal.account.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springpractice.buckpal.account.application.port.in.SendMoneyCommand;
import com.example.springpractice.buckpal.account.application.port.in.SendMoneyUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SendMoneyController {

	private final SendMoneyUseCase sendMoneyUseCase;

	@PostMapping( "/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
	public ResponseEntity<Void> sendMoney(
			@PathVariable("sourceAccountId") final Long sourceAccountId,
			@PathVariable("targetAccountId") final Long targetAccountId,
			@PathVariable("amount") final Long amount
	) {
		sendMoneyUseCase.sendMoney(SendMoneyCommand.of(sourceAccountId, targetAccountId, amount));
		return ResponseEntity.ok().build();
	}
}
