package com.ureca.gate.plan.infrastructure.jpaadapter;

import com.ureca.gate.plan.infrastructure.jpaadapter.entity.PlanDogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanDogJpaRepository extends JpaRepository<PlanDogEntity, Long> {
    List<PlanDogEntity> findAllByDogId(Long dogId);
}
