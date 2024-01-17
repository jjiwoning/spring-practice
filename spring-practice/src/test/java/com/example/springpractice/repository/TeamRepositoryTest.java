package com.example.springpractice.repository;

import com.example.springpractice.member.domain.Member;
import com.example.springpractice.member.domain.Team;
import com.example.springpractice.member.repository.MemberRepository;
import com.example.springpractice.member.repository.TeamRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;
    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("oneToMany 일급 컬렉션 테스트")
    void test1() {
        Team team = Team.builder().name("테스트").build();

        teamRepository.save(team);

        Member member1 = Member.builder().name("테스트1").nickname("테스트1").team(team).build();
        Member member2 = Member.builder().name("테스트2").nickname("테스트2").team(team).build();
        Member member3 = Member.builder().name("테스트3").nickname("테스트3").team(team).build();

        Team findTeam = teamRepository.findById(team.getId()).get();

        findTeam.getMembers().addMember(member1);
        findTeam.getMembers().addMember(member2);
        findTeam.getMembers().addMember(member3);

        em.flush();

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        Member findMember3 = memberRepository.findById(member3.getId()).get();

        System.out.println(findMember1);
        System.out.println(findMember2);
        System.out.println(findMember3);
    }
}