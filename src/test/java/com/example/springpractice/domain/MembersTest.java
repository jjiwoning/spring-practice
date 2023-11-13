package com.example.springpractice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Members 테스트")
class MembersTest {

	@Test
	@DisplayName("이벤트 발행 확인 테스트")
	void test1() {
		Members members = new Members();
		members.addMember(Member.builder().id(1L).build());
	}
}