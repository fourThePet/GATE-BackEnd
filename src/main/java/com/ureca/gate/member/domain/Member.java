package com.ureca.gate.member.domain;

import com.ureca.gate.member.domain.Enum.Gender;
import com.ureca.gate.member.domain.Enum.Role;
import com.ureca.gate.member.domain.Enum.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Member {
    private Long id;
    private String nickName;
    private OauthInfo oauthInfo;
    private Integer age;
    private Gender gender;
    private Role role;
    private Status status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Builder
    public Member(Long id, String nickName, OauthInfo oauthInfo, Integer age, Gender gender, Role role,Status status, LocalDateTime createAt, LocalDateTime updateAt){
        this.id = id;
        this.nickName = nickName;
        this.oauthInfo =oauthInfo;
        this.age =age;
        this.gender = gender;
        this.role = role;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

}