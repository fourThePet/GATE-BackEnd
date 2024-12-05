package com.ureca.gate.plan.infrastructure.jpaadapter;

import com.ureca.gate.global.domain.CustomPage;
import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.global.exception.errorcode.CommonErrorCode;
import com.ureca.gate.plan.application.outputport.PlanRepository;
import com.ureca.gate.plan.domain.Plan;
import com.ureca.gate.plan.infrastructure.jpaadapter.command.PlanCommand;
import com.ureca.gate.plan.infrastructure.jpaadapter.command.PlanSearchCondition;
import com.ureca.gate.plan.infrastructure.jpaadapter.entity.PlanEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PlanRepositoryImpl implements PlanRepository {

    private final PlanJpaRepository planJpaRepository;

    @Override
    public CustomPage<PlanCommand> searchPage(PlanSearchCondition planSearchCondition, Pageable pageable) {
        Page<PlanCommand> planCommandCustomPage = planJpaRepository.searchPage(planSearchCondition, pageable);
        return CustomPage.from(planCommandCustomPage);
    }

    @Override
    public Plan getById(Long planId) {
        return planJpaRepository.findById(planId)
                .map(PlanEntity::toModel)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public Plan save(Plan plan) {
        return planJpaRepository.save(PlanEntity.from(plan)).toModel();
    }

    @Override
    public void delete(Plan plan) {
        planJpaRepository.delete(PlanEntity.from(plan));
    }
}
