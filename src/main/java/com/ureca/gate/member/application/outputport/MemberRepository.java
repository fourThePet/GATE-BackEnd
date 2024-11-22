package com.ureca.gate.member.application.outputport;

import com.ureca.gate.member.domain.Member;
import com.ureca.gate.member.domain.OauthInfo;

import java.util.Optional;

public interface MemberRepository {
    public Optional<Member> findByOauthInfoOid(String Oid);
    public Member forceJoin(OauthInfo oauthInfo);
    public Member save(Member member);
    public Optional<Member> findById(Long id);
    public boolean existsByNickname(String nickName);

}
