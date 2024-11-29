package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.place.application.outputport.CityRepository;
import com.ureca.gate.place.domain.City;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.CityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CityRepositoryImpl implements CityRepository {

    private final CityJpaRepository cityJpaRepository;
    @Override
    public List<City> findAll() {
        return cityJpaRepository.findAll().stream()
                .map(CityEntity::toModel)
                .toList();
    }
}
