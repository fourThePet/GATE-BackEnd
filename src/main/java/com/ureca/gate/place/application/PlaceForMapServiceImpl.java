package com.ureca.gate.place.application;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.favorites.controller.inputport.FavoritesService;
import com.ureca.gate.place.application.outputport.GeoEmbedApiService;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.controller.inputport.PlaceForMapService;
import com.ureca.gate.place.controller.response.PlaceForMapResponse;
import com.ureca.gate.place.domain.enumeration.YesNo;
import com.ureca.gate.place.domain.vo.Address;
import com.ureca.gate.place.infrastructure.command.GeoEmbed;
import com.ureca.gate.place.infrastructure.command.PlaceCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PlaceForMapServiceImpl implements PlaceForMapService {
    private final PlaceRepository placeRepository;
    private final FavoritesService favoritesService;
    private final GeoEmbedApiService geoEmbedApiService;
    private final PlaceSearchService placeService;

    private static final GeometryFactory geometryFactory =  new GeometryFactory(new PrecisionModel(), 4326);


    @Override
    public List<PlaceForMapResponse> getPlacesForMap(Long memberId, Double latitude, Double longitude, String query, String category, Size size, List<String> entryConditions, List<String> types) {

        Point userLocation = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        userLocation.setSRID(4326); // SRID 4326 (WGS 84 좌표계)로 설정

        List<PlaceCommand> places;

        if (isQueryEmpty(query)) {
            // query가 없으면 기존 방식으로 장소를 가져옵니다.
            places = placeRepository.findByQueryDsl(userLocation, category, size, entryConditions, types);
        } else {
            // query 값이 있으면 벡터 DB에서 유사한 장소들을 검색합니다.
            places = getPlacesByEmbedding(query, userLocation, category, size, entryConditions, types);
        }

        //결과 매핑 및 즐겨찾기 상태 추가
        return places.stream()
                .map(place -> mapToPlaceResponseWithFavoriteStatus(place, memberId))
                .collect(Collectors.toList());
    }

    private boolean isQueryEmpty(String query) {
        return query == null || query.trim().isEmpty();
    }

    private List<PlaceCommand> getPlacesByEmbedding(String query, Point userLocation, String category, Size size, List<String> entryConditions, List<String> types) {
        GeoEmbed apiResponse = geoEmbedApiService.extractEmbeddingAndRegion(query);  // GeoEmbed 호출

        Address address = (apiResponse.getRegions() == null || apiResponse.getRegions().isEmpty())
                ? null
                : Address.fromRegionString(apiResponse.getRegions());  // 지역 정보가 있으면 Address 객체 생성

        // address가 null일 경우 searchByEmbeddingOnly 호출, 아니면 searchByEmbedding 호출
        return placeService.searchByEmbedding(apiResponse.getEmbedding(), address, userLocation, category, size, entryConditions, types);
    }

    // 즐겨찾기 상태 매핑 메서드
    private PlaceForMapResponse mapToPlaceResponseWithFavoriteStatus(PlaceCommand place, Long memberId) {
        YesNo isFavorite = favoritesService.checkIfFavorite(memberId, place.getId());
        return PlaceForMapResponse.from(place, isFavorite);
    }
}

