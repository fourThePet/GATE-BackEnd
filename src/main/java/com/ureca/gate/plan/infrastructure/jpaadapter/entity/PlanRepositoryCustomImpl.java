package com.ureca.gate.plan.infrastructure.jpaadapter.entity;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ureca.gate.plan.domain.DateFilter;
import com.ureca.gate.plan.domain.SortOrder;
import com.ureca.gate.plan.infrastructure.jpaadapter.PlanRepositoryCustom;
import com.ureca.gate.plan.infrastructure.jpaadapter.command.PlanCommand;
import com.ureca.gate.plan.infrastructure.jpaadapter.command.PlanSearchCondition;
import com.ureca.gate.plan.infrastructure.jpaadapter.command.QPlanCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.ureca.gate.plan.infrastructure.jpaadapter.entity.QPlanEntity.planEntity;

@RequiredArgsConstructor
public class PlanRepositoryCustomImpl implements PlanRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PlanCommand> searchPage(PlanSearchCondition planSearchCondition, Pageable pageable) {
        List<PlanCommand> plans = queryFactory
                .select(new QPlanCommand(
                        planEntity.id,
                        planEntity.city.name,
                        planEntity.date,
                        planEntity.planDogs.size()))
                .from(planEntity)
                .where(
                        planEntity.memberId.eq(planSearchCondition.getMemberId()),
                        dateThan(planSearchCondition.getDateFilter())
                )
                .orderBy(
                        orderBySortOrder(planSearchCondition.getSortOrder())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(planEntity.count())
                .from(planEntity)
                .where(
                        planEntity.memberId.eq(planSearchCondition.getMemberId()),
                        dateThan(planSearchCondition.getDateFilter())
                )
                .fetchOne();

        return new PageImpl<>(plans, pageable, Optional.ofNullable(total).orElse(0L));
    }

    private static OrderSpecifier<Long> orderBySortOrder(SortOrder sortOrder) {
        if (sortOrder.isDesc()) {
            return planEntity.id.desc();
        }
        return planEntity.id.asc();
    }

    private BooleanExpression dateThan(DateFilter dateFilter) {
        LocalDate today = LocalDate.now();

        if (dateFilter.isBefore()) {
            return planEntity.date.lt(today);
        }
        if (dateFilter.isAfter()) {
            return planEntity.date.goe(today);
        }
        return null;
    }
}
