package com.ureca.gate.member.infrastructure.jpaadapter.entity;


import com.ureca.gate.global.entity.BaseTimeEntity;
import com.ureca.gate.member.domain.enumeration.Gender;
import com.ureca.gate.member.domain.enumeration.Role;
import com.ureca.gate.member.domain.enumeration.Status;
import com.ureca.gate.member.domain.Member;
import com.ureca.gate.member.domain.OauthInfo;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "member")
@Getter
public class MemberEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickName;

    private LocalDate birthday;

    @Embedded
    private OauthInfo oauthInfo;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    public static MemberEntity fromOauth(OauthInfo oauthInfo){
        MemberEntity userEntity = new MemberEntity();
        userEntity.oauthInfo = oauthInfo;
        userEntity.role = Role.USER;
        userEntity.status = Status.NONACTIVE;
        return userEntity;
    }
    public static MemberEntity from(Member member){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.id = member.getId();
        memberEntity.oauthInfo = member.getOauthInfo();
        memberEntity.nickName = member.getNickName();
        memberEntity.birthday = member.getBirthday();
        memberEntity.gender = member.getGender();
        memberEntity.role = member.getRole();
        memberEntity.status = member.getStatus();
        return memberEntity;
    }

    public Member toModel(){
        return Member.builder()
                .id(id)
                .oauthInfo(oauthInfo)
                .nickName(nickName)
                .role(role)
                .birthday(birthday)
                .gender(gender)
                .status(status)
                .createAt(getCreateAt())  // BaseTimeEntity에서 상속받은 createAt
                .updateAt(getUpdateAt())  // BaseTimeEntity에서 상속받은 updateAt
                .build();
    }

}
