package com.ureca.gate.member.infrastructure.jpaAdapter;


import com.ureca.gate.member.infrastructure.jpaAdapter.Entitiy.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity,Long> {
    Optional<MemberEntity> findByOauthInfoOid(String oid);
}
