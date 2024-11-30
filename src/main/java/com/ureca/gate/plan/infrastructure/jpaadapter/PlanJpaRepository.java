package com.ureca.gate.plan.infrastructure.jpaadapter;

import com.ureca.gate.plan.infrastructure.jpaadapter.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanJpaRepository extends JpaRepository<PlanEntity, Long> {
}
