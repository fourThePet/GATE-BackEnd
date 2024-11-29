package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.infrastructure.dto.PlaceResponse;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepository {

    private final PlaceJpaRepository placeJpaRepository;

    @Override
    public Optional<Place> findById(Long placeId) {
        return placeJpaRepository.findById(placeId).map(PlaceEntity::toModel);
    }

    @Override
    public List<PlaceResponse> findByQueryDsl(Point userLocation, String category, Size size, List<String> entryConditions, List<String> types){
        return placeJpaRepository.findByQueryDsl(userLocation,category,size,entryConditions,types);

    }



}
