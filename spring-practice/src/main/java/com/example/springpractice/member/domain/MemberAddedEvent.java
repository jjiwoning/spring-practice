package com.example.springpractice.member.domain;

import lombok.Getter;

@Getter
public class MemberAddedEvent {

	private final Long memberId;

	public MemberAddedEvent(Long memberId) {
		this.memberId = memberId;
	}
}
