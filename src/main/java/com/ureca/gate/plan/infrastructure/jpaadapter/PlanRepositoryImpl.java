package com.ureca.gate.plan.infrastructure.jpaadapter;

import com.ureca.gate.plan.application.outputport.PlanRepository;
import com.ureca.gate.plan.domain.Plan;
import com.ureca.gate.plan.infrastructure.jpaadapter.entity.PlanEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PlanRepositoryImpl implements PlanRepository {

    private final PlanJpaRepository planJpaRepository;

    @Override
    public Plan save(Plan plan) {
        return planJpaRepository.save(PlanEntity.from(plan)).toModel();
    }
}
