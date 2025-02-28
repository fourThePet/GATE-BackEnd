package com.ureca.gate.place.application;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.favorites.controller.inputport.FavoritesService;
import com.ureca.gate.global.domain.CustomPage;
import com.ureca.gate.global.dto.response.PageResponse;
import com.ureca.gate.global.util.place.PlaceMapper;
import com.ureca.gate.place.application.outputport.GeoEmbedApiService;
import com.ureca.gate.place.application.outputport.PlaceElasticRepository;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.controller.inputport.PlaceForMapService;
import com.ureca.gate.place.controller.response.PlaceForMapResponse;
import com.ureca.gate.place.controller.response.PlaceSearchForMapResponse;
import com.ureca.gate.place.domain.enumeration.YesNo;
import com.ureca.gate.place.domain.vo.Address;
import com.ureca.gate.place.infrastructure.command.GeoEmbed;
import com.ureca.gate.place.infrastructure.command.PlaceCommand;
import com.ureca.gate.place.infrastructure.command.PlaceSearchCommand;
import com.ureca.gate.review.application.outputport.GptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PlaceForMapServiceImpl implements PlaceForMapService {
    private final PlaceRepository placeRepository;
    private final FavoritesService favoritesService;
    private final PlaceElasticRepository placeElasticRepository;
    private final GptService gptService;

    private static final GeometryFactory geometryFactory =  new GeometryFactory(new PrecisionModel(), 4326);


    @Override
    public List<PlaceForMapResponse> getPlacesForMap(Long memberId, Double latitude, Double longitude,Double curLatitude, Double curLongitude, String query, String category, Size size, List<String> entryConditions, List<String> types) {

        List<PlaceCommand> places = null;

        List<PlaceSearchCommand> placeSearchCommands = null;

            if (isQueryEmpty(query)) {  //검색어가 존재 X : 반경 검색
                Point userLocation = geometryFactory.createPoint(new Coordinate(curLatitude, curLongitude)); // 반경을 옮겼을때의 위도,경도
                userLocation.setSRID(4326); // SRID 4326 (WGS 84 좌표계)로 설정

                places = placeRepository.findByQueryDsl(userLocation, latitude, longitude, category, size, entryConditions, types);

                return places.stream()
                        .map(place -> mapToPlaceResponseWithFavoriteStatus(place, memberId))
                        .collect(Collectors.toList());
            }
            else { //검색어 조회
                placeSearchCommands = getPlacesBySearch(latitude, longitude, query, category, size, entryConditions, types);

                return placeSearchCommands.stream()
                    .map(p -> mapToPlaceSearchResponseWithFavoriteStatus(p, memberId))
                    .toList();
        }
    }


//    @Override
    public List<PlaceSearchCommand> getPlacesBySearch(Double latitude, Double longitude, String query, String category, Size size, List<String> entryConditions, List<String> types) {

        String answer = gptService.getRegion(query); //지역명 추출

        Map<String, String> regionAndQuery = PlaceMapper.mapResponse(answer);

        String city = regionAndQuery.getOrDefault("시", null);
        String district = regionAndQuery.getOrDefault("군/구", null);
        String town = regionAndQuery.getOrDefault("동/리", null);
        String searchQuery = regionAndQuery.getOrDefault("검색어", city);

        //해당 값 추후 수정 예정
        if(searchQuery.isEmpty()){
            StringBuilder searchQueryBuilder = new StringBuilder();
            if (city != null) searchQueryBuilder.append(city).append(" ");
            if (district != null) searchQueryBuilder.append(district).append(" ");
            if (town != null) searchQueryBuilder.append(town);

            searchQuery = searchQueryBuilder.toString().trim();
        }

        return placeElasticRepository.findSimilarPlacesByLocation(latitude, longitude, searchQuery, category,city,district,town,size,entryConditions,types);
    }


    //검색어 존재 여부
    private boolean isQueryEmpty(String query) {
        return query == null || query.trim().isEmpty();
    }

    // 즐겨찾기 상태 매핑 메서드
    private PlaceForMapResponse mapToPlaceResponseWithFavoriteStatus(PlaceCommand place, Long memberId) {
        YesNo isFavorite = favoritesService.checkIfFavorite(memberId, place.getId());
        return PlaceForMapResponse.from(place, isFavorite);
    }

    private PlaceForMapResponse mapToPlaceSearchResponseWithFavoriteStatus(PlaceSearchCommand place, Long memberId) {
        YesNo isFavorite = favoritesService.checkIfFavorite(memberId, place.getId());
        return PlaceForMapResponse.from(place, isFavorite);
    }

}

