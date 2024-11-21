package com.ureca.gate.member.infrastructure.jpaAdapter;


import com.ureca.gate.member.application.outputport.MemberRepository;
import com.ureca.gate.member.domain.Member;
import com.ureca.gate.member.domain.OauthInfo;
import com.ureca.gate.member.infrastructure.jpaAdapter.Entitiy.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> findByOauthInfoOid(String Oid) {
        return memberJpaRepository.findByOauthInfoOid(Oid).map(MemberEntity::toModel);
    }

    @Override
    @Transactional
    public Member save(Member member) {
        return memberJpaRepository.save(MemberEntity.from(member)).toModel();
    }


    @Override
    @Transactional
    public Member forceJoin(OauthInfo oauthInfo) {
        MemberEntity newMemberEntity = MemberEntity.fromOauth(oauthInfo);
        return save(newMemberEntity.toModel());
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberJpaRepository.findById(id).map(MemberEntity::toModel);
    }

}
