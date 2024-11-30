package com.ureca.gate.plan.infrastructure.jpaadapter.entity;

import com.ureca.gate.global.entity.BaseTimeEntity;
import com.ureca.gate.plan.domain.Plan;
import com.ureca.gate.plan.domain.PlanPlace;
import jakarta.persistence.*;

@Entity
@Table(name = "plan_places")
public class PlanPlaceEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_place_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private PlanEntity plan;

    private Long placeId;

    private Integer sequence;

    public void setPlan(PlanEntity planEntity) {
        this.plan = planEntity;
        planEntity.getPlanPlaces().add(this);
    }

    public static PlanPlaceEntity from(PlanPlace planPlace) {
        Long planId = planPlace.getPlanId();
        Plan plan = Plan.builder()
                .id(planId)
                .build();
        return from(planPlace, plan);
    }

    private static PlanPlaceEntity from(PlanPlace planPlace, Plan plan) {
        PlanPlaceEntity planPlaceEntity = new PlanPlaceEntity();
        planPlaceEntity.id = planPlace.getId();
        planPlaceEntity.plan = PlanEntity.from(plan);
        planPlaceEntity.placeId = planPlace.getPlaceId();
        planPlaceEntity.sequence = planPlace.getSequence();
        return planPlaceEntity;
    }

    public PlanPlace toModel() {
        return PlanPlace.builder()
                .id(id)
                .planId(plan.getId())
                .placeId(placeId)
                .sequence(sequence)
                .build();
    }
}
