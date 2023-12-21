package com.example.springpractice.member.repository;

import com.example.springpractice.member.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
