package com.example.springpractice.domain.vo;

import com.example.springpractice.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.List;

@Embeddable
public class Members {

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> members;

}