package com.ureca.gate.place.application;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.favorites.application.outputport.FavoritesRepository;
import com.ureca.gate.favorites.controller.inputport.FavoritesService;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.controller.inputport.PlaceListService;
import com.ureca.gate.place.controller.response.PlaceResponse;
import com.ureca.gate.place.domain.enumeration.YesNo;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceListServiceImpl implements PlaceListService {
    private final PlaceRepository placeRepository;
    private final FavoritesService favoritesService;
    private static final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    public List<PlaceResponse> getPlaceList(Long memberId, Double latitude, Double longitude, String category, Size size, List<String> entryConditions, List<String> types) {

        Point userLocation = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        userLocation.setSRID(4326); // SRID 4326 (WGS 84 좌표계)로 설정

        return placeRepository.findByQueryDsl(userLocation,category,size,entryConditions,types).stream()
                .map(place -> {
                    YesNo isFavorite = favoritesService.checkIfFavorite(memberId, place.getId());
                    return PlaceResponse.from(place, isFavorite);})
                .collect(Collectors.toList());

    }
}
