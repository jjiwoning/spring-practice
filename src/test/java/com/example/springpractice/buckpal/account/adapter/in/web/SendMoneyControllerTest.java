package com.example.springpractice.buckpal.account.adapter.in.web;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.springpractice.buckpal.account.application.port.in.SendMoneyCommand;
import com.example.springpractice.buckpal.account.application.port.in.SendMoneyUseCase;
import com.example.springpractice.buckpal.account.domain.Account;
import com.example.springpractice.buckpal.account.domain.Money;
import com.example.springpractice.member.auth.JwtUtil;

@WebMvcTest(controllers = SendMoneyController.class)
class SendMoneyControllerTest {

	@MockBean
	private JwtUtil jwtUtil;

	@MockBean
	private SendMoneyUseCase sendMoneyUseCase;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void test1() throws Exception {
		mockMvc.perform(post("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}",
				41L, 42L, 500)
				.header("Content-Type", "application/json"))
			.andExpect(status().isOk());

		then(sendMoneyUseCase).should()
			.sendMoney(eq(SendMoneyCommand.of(
				new Account.AccountId(41L).getValue(),
				new Account.AccountId(42L).getValue(),
				Money.of(500L).getAmount().longValue())));
	}
}
