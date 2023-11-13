package com.example.springpractice.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import com.example.springpractice.event.Events;

@Embeddable
@Getter
public class Members {

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    public void addMember(Member member) {
        members.add(member);
        Events.raise(new MemberAddedEvent(member.getId()));
    }

    public int getMembersCount() {
        return members.size();
    }

}
