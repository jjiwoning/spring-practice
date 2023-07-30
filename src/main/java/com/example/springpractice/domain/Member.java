package com.example.springpractice.domain;

import com.example.springpractice.domain.vo.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    @Builder
    public Member(Long id, String name, String nickname, Address address) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.address = address;
    }
}
