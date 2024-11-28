package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepository {

    private final PlaceJpaRepository placeJpaRepository;
    @Override
    public Optional<Place> findById(Long placeId) {
        return placeJpaRepository.findById(placeId).map(PlaceEntity::toModel);
    }
}
