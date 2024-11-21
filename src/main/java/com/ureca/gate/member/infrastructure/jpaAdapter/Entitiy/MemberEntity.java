package com.ureca.gate.member.infrastructure.jpaAdapter.Entitiy;


import com.ureca.gate.member.domain.Enum.Gender;
import com.ureca.gate.member.domain.Enum.Role;
import com.ureca.gate.member.domain.Member;
import com.ureca.gate.member.domain.OauthInfo;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private Integer age;

    private Gender gender;

    @Embedded
    private OauthInfo oauthInfo;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static MemberEntity fromOauth(OauthInfo oauthInfo){
        MemberEntity userEntity = new MemberEntity();
        userEntity.oauthInfo = oauthInfo;
        userEntity.role = Role.USER;
        return userEntity;
    }
    public static MemberEntity from(Member member){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.oauthInfo = member.getOauthInfo();
        memberEntity.age = member.getAge();
        memberEntity.gender = member.getGender();
        memberEntity.role = member.getRole();
        return memberEntity;
    }

    public Member toModel(){
        return Member.builder()
                .id(id)
                .oauthInfo(oauthInfo)
                .role(role)
                .build();
    }

}
