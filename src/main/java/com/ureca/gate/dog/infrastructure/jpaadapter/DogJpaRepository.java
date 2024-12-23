package com.ureca.gate.dog.infrastructure.jpaadapter;

import com.ureca.gate.dog.infrastructure.jpaadapter.entity.DogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogJpaRepository extends JpaRepository<DogEntity, Long> {
    List<DogEntity> findByMemberId(Long memberId);
}
