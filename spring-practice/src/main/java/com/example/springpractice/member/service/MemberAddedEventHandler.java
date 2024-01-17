package com.example.springpractice.member.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.springpractice.member.domain.MemberAddedEvent;

@Service
public class MemberAddedEventHandler {

	@EventListener(MemberAddedEvent.class)
	public void handle(MemberAddedEvent memberAddedEvent) {
		System.out.println("hello ;p " + memberAddedEvent.getMemberId());
	}
}
