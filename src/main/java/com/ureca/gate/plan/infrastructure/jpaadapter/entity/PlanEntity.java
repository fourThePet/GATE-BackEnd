package com.ureca.gate.plan.infrastructure.jpaadapter.entity;

import com.ureca.gate.global.entity.BaseTimeEntity;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.CityEntity;
import com.ureca.gate.plan.domain.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.*;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
@DynamicUpdate
@SQLDelete(sql = "UPDATE plans SET delete_yn = 'Y' WHERE plan_id = ?")
@Where(clause = "delete_yn = 'N'")
@Entity(name = "plans")
public class PlanEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    private Long memberId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "city_id")
    private CityEntity city;

    private LocalDate date;

    private String deleteYn = "N";

    @OneToMany(mappedBy = "plan", cascade = ALL, orphanRemoval = true)
    private Set<PlanDogEntity> planDogs = new HashSet<>();

    @OneToMany(mappedBy = "plan", cascade = ALL, orphanRemoval = true)
    private Set<PlanPlaceEntity> planPlaces = new HashSet<>();

    private void addPlanDog(PlanDogEntity planDogEntity) {
        planDogs.add(planDogEntity);
        planDogEntity.setPlan(this);
    }

    private void addPlanPlace(PlanPlaceEntity planPlaceEntity) {
        planPlaces.add(planPlaceEntity);
        planPlaceEntity.setPlan(this);
    }

    public static PlanEntity from(Plan plan) {
        PlanEntity planEntity = new PlanEntity();
        planEntity.id = plan.getId();
        planEntity.memberId = plan.getMemberId();
        planEntity.city = Optional.ofNullable(plan.getCity())
                .map(CityEntity::from)
                .orElse(null);
        planEntity.date = plan.getDate();
        plan.getPlanDogs().forEach(planDog -> planEntity.addPlanDog(PlanDogEntity.from(planDog)));
        plan.getPlanPlaces().forEach(planPlace -> planEntity.addPlanPlace(PlanPlaceEntity.from(planPlace)));
        return planEntity;
    }

    public Plan toModel() {
        return Plan.builder()
                .id(id)
                .memberId(memberId)
                .city(city.toModel())
                .date(date)
                .planDogs(planDogs.stream().map(PlanDogEntity::toModel).toList())
                .planPlaces(planPlaces.stream().map(PlanPlaceEntity::toModel).toList())
                .build();
    }
}
