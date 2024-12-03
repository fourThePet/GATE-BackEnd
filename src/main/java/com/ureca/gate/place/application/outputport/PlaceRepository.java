package com.ureca.gate.place.application.outputport;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.place.domain.Category;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.infrastructure.dto.PlaceResponse;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository {
    Optional<Place> findById(Long placeId);

    List<PlaceResponse> findByQueryDsl(Point userLocation, String category, Size size, List<String> entryConditions, List<String> types);

    Place getById(Long placeId);
}
