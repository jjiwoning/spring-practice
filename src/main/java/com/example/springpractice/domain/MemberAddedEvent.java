package com.example.springpractice.domain;

import lombok.Getter;

@Getter
public class MemberAddedEvent {

	private final Long memberId;

	public MemberAddedEvent(Long memberId) {
		this.memberId = memberId;
	}
}
