package com.ureca.gate.plan.infrastructure.jpaadapter;

import com.ureca.gate.plan.infrastructure.jpaadapter.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlanJpaRepository extends JpaRepository<PlanEntity, Long>, PlanRepositoryCustom {

    @Query("SELECT p FROM plans p " +
            "JOIN FETCH p.planPlaces pp " +
            "JOIN FETCH pp.place pl " +
            "JOIN FETCH pl.addressEntity.cityEntity " +
            "WHERE p.id = :planId")
    Optional<PlanEntity> findById(Long planId);
}
