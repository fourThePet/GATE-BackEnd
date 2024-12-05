package com.ureca.gate.plan.infrastructure.jpaadapter.entity;

import com.ureca.gate.global.entity.BaseTimeEntity;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import com.ureca.gate.plan.domain.Plan;
import com.ureca.gate.plan.domain.PlanPlace;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "plan_places")
@SQLDelete(sql = "UPDATE plan_places SET delete_yn = 'Y' WHERE plan_place_id = ?")
@Where(clause = "delete_yn = 'N'")
public class PlanPlaceEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "plan_place_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private PlanEntity plan;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    private PlaceEntity place;

    private Integer sequence;

    private String deleteYn = "N";

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
        planPlaceEntity.place = PlaceEntity.from(planPlace.getPlace());
        planPlaceEntity.sequence = planPlace.getSequence();
        return planPlaceEntity;
    }

    public PlanPlace toModel() {
        return PlanPlace.builder()
                .id(id)
                .planId(plan.getId())
                .place(place.toModel())
                .sequence(sequence)
                .build();
    }
}
