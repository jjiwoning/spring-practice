package com.example.springpractice.domain;

import com.example.springpractice.domain.vo.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String nickname;
    @Embedded
    private Address address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Member(Long id, String name, String nickname, Address address, Team team) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.address = address;
        this.team = team;
    }
}
