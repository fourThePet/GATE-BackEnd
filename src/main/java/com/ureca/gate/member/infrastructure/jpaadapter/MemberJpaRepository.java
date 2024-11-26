package com.ureca.gate.member.infrastructure.jpaadapter;


import com.ureca.gate.member.infrastructure.jpaadapter.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity,Long> {
    Optional<MemberEntity> findByOauthInfoOid(String oid);

    boolean existsByNickName(String nickName);
}
