package com.ureca.gate.plan.infrastructure.jpaadapter;

import com.ureca.gate.plan.infrastructure.jpaadapter.entity.PlanDogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanDogJpaRepository extends JpaRepository<PlanDogEntity, Long> {
}
