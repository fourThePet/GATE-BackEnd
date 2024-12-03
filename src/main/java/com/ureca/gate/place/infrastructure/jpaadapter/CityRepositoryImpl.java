package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.place.application.outputport.CityRepository;
import com.ureca.gate.place.domain.City;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.CityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.CITY_NOT_FOUND;

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

    @Override
    public City getById(Long cityId) {
        return cityJpaRepository.findById(cityId)
                .map(CityEntity::toModel)
                .orElseThrow(() -> new BusinessException(CITY_NOT_FOUND));
    }
}
