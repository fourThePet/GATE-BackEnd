package com.ureca.gate.plan.infrastructure.jpaadapter;

import com.ureca.gate.plan.application.outputport.PlanPlaceRepository;
import com.ureca.gate.plan.domain.PlanPlace;
import com.ureca.gate.plan.infrastructure.jpaadapter.entity.PlanPlaceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PlanPlaceRepositoryImpl implements PlanPlaceRepository {

    private final PlanPlaceJpaRepository planPlaceJpaRepository;

    @Override
    public void deleteAll(List<PlanPlace> planPlaces) {
        planPlaceJpaRepository.deleteAll(planPlaces.stream().map(PlanPlaceEntity::from).toList());
    }
}
