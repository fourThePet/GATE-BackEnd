package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.place.infrastructure.command.PlaceCommand;
import org.locationtech.jts.geom.Point;

import java.util.List;


public interface PlaceRepositoryCustom {
    List<PlaceCommand> findByQueryDsl(Point userLocation, String category, Size size, List<String> entryConditions, List<String> types);

    List<PlaceCommand> findByVectorSearchAndQueryDsl(
            List<Long> placeIds,
            Point userLocation,
            String category,
            Size size,
            List<String> entryConditions,
            List<String> types);
}
