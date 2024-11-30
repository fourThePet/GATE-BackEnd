package com.ureca.gate.plan.infrastructure.jpaadapter.entity;

import com.ureca.gate.global.entity.BaseTimeEntity;
import com.ureca.gate.plan.domain.Plan;
import com.ureca.gate.plan.domain.PlanDog;
import jakarta.persistence.*;

@Entity(name = "plan_dogs")
public class PlanDogEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_dog_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private PlanEntity plan;

    private Long dogId;

    public void setPlan(PlanEntity planEntity) {
        this.plan = planEntity;
        planEntity.getPlanDogs().add(this);
    }

    public static PlanDogEntity from(PlanDog planDog) {
        Long planId = planDog.getPlanId();
        Plan plan = Plan.builder()
                .id(planId)
                .build();
        return from(planDog, plan);
    }

    private static PlanDogEntity from(PlanDog planDog, Plan plan) {
        PlanDogEntity planDogEntity = new PlanDogEntity();
        planDogEntity.id = planDog.getId();
        planDogEntity.plan = PlanEntity.from(plan);
        planDogEntity.dogId = planDog.getDogId();
        return planDogEntity;
    }

    public PlanDog toModel() {
        return PlanDog.builder()
                .id(id)
                .planId(plan.getId())
                .dogId(dogId)
                .build();
    }
}
