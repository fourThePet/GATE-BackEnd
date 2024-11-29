package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.place.infrastructure.jpaadapter.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityJpaRepository extends JpaRepository<CityEntity,Long> {
}
