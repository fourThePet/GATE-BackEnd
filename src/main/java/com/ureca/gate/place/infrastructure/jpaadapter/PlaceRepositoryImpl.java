package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.infrastructure.command.PlaceCommand;
import com.ureca.gate.place.infrastructure.command.PlaceDistanceDto;
import com.ureca.gate.place.infrastructure.command.PlaceForPlanCommand;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.PLACE_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepository {

    private final PlaceJpaRepository placeJpaRepository;

    @Override
    public Optional<Place> findById(Long placeId) {
        return placeJpaRepository.findById(placeId).map(PlaceEntity::toModel);
    }

    @Override
    public List<PlaceCommand> findByQueryDsl(Point userLocation, Double latitude, Double longitude, String category, Size size, List<String> entryConditions, List<String> types){
        return placeJpaRepository.findByQueryDsl(userLocation, latitude, longitude,category,size,entryConditions,types);

    }

    @Override
    public List<PlaceCommand> findByVectorSearchAndQueryDsl(List<Long> placeIds, Point userLocation, String category, Size size, List<String> entryConditions, List<String> types) {
        return placeJpaRepository.findByVectorSearchAndQueryDsl(placeIds,userLocation,category,size,entryConditions,types);
    }

    @Override
    public List<PlaceForPlanCommand> findPlaceForPlanResponseByIdIn(List<Long> placeIds) {
        return placeJpaRepository.findPlaceForPlanResponseByIdIn(placeIds);
    }

    @Override
    public Place getById(Long placeId) {
        return placeJpaRepository.findById(placeId)
                .map(PlaceEntity::toModel)
                .orElseThrow(() -> new BusinessException(PLACE_NOT_FOUND));
    }


    @Override
    public Double calculrateDistance(Double longitude, Double latitude, Long placeId) {
        return placeJpaRepository.calculateDistances(longitude,latitude,placeId);
    }


}
