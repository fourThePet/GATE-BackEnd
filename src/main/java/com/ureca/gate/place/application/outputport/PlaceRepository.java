package com.ureca.gate.place.application.outputport;

import com.ureca.gate.place.domain.Place;

import java.util.Optional;

public interface PlaceRepository {
    Optional<Place> findById(Long placeId);
}
