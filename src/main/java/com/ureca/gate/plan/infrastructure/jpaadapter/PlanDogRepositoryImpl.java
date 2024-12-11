package com.ureca.gate.plan.infrastructure.jpaadapter;

import com.ureca.gate.plan.application.outputport.PlanDogRepository;
import com.ureca.gate.plan.domain.PlanDog;
import com.ureca.gate.plan.infrastructure.jpaadapter.entity.PlanDogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PlanDogRepositoryImpl implements PlanDogRepository {

    private final PlanDogJpaRepository planDogJpaRepository;

    @Override
    public List<PlanDog> findAllByDogId(Long dogId) {
        return planDogJpaRepository.findAllByDogId(dogId)
                .stream().map(PlanDogEntity::toModel)
                .toList();
    }

    @Override
    public void deleteAll(List<PlanDog> planPlaces) {
        List<PlanDogEntity> entities = planPlaces.stream().map(PlanDogEntity::from).toList();
        planDogJpaRepository.deleteAll(entities);
    }
}
