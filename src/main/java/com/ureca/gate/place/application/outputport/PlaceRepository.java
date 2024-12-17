package com.ureca.gate.place.application.outputport;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.infrastructure.command.PlaceCommand;
import com.ureca.gate.place.infrastructure.command.PlaceDistanceDto;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository {
    Optional<Place> findById(Long placeId);
    Place getById(Long placeId);

    List<PlaceCommand> findByQueryDsl(
            Point userLocation,
            String category,
            Size size,
            List<String> entryConditions,
            List<String> types
    );

    List<PlaceCommand> findByVectorSearchAndQueryDsl(
            List<Long> placeIds,
            Point userLocation,
            String category,
            Size size,
            List<String> entryConditions,
            List<String> types);

    Double calculrateDistance(Double longitude, Double latitude, Long placeId);

}
