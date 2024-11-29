package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.place.infrastructure.dto.PlaceResponse;
import org.locationtech.jts.geom.Point;

import java.util.List;


public interface PlaceRepositoryCustom {
    List<PlaceResponse> findByQueryDsl(Point userLocation, String category, Size size, List<String> entryConditions, List<String> types);

}
