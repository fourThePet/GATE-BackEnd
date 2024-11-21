package com.ureca.gate.member.domain;

import com.ureca.gate.member.domain.Enum.Gender;
import com.ureca.gate.member.domain.Enum.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {
    private Long id;
    private OauthInfo oauthInfo;
    private Integer age;
    private Gender gender;
    private Role role;

    @Builder
    public Member(Long id, OauthInfo oauthInfo, Integer age, Gender gender, Role role){
        this.id = id;
        this.oauthInfo =oauthInfo;
        this.age =age;
        this.gender = gender;
        this.role = role;
    }

}