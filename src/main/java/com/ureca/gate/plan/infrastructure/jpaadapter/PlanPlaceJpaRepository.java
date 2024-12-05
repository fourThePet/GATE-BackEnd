package com.ureca.gate.plan.infrastructure.jpaadapter;

import com.ureca.gate.plan.infrastructure.jpaadapter.entity.PlanPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanPlaceJpaRepository extends JpaRepository<PlanPlaceEntity, Long> {
}
