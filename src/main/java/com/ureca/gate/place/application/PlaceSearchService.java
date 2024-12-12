package com.ureca.gate.place.application;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.application.outputport.PlaceVectorRepository;
import com.ureca.gate.place.domain.vo.Address;
import com.ureca.gate.place.infrastructure.command.PlaceCommand;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlaceSearchService {
    private final PlaceVectorRepository placeVectorRepository;
    private final PlaceRepository placeRepository;

    public List<PlaceCommand> searchByEmbedding(float[] queryEmbedding, Address address, Point userLocation, String category, Size size, List<String> entryConditions, List<String> types) {
        if (address == null) {
            return searchByEmbeddingOnly(queryEmbedding, userLocation, category, size, entryConditions, types);
        }
        // 지역 정보가 있을 경우
        return searchByEmbeddingWithRegion(queryEmbedding, address, userLocation, category, size, entryConditions, types);
    }

    public List<PlaceCommand> searchByEmbeddingWithRegion(float[] queryEmbedding, Address address, Point userLocation, String category, Size size, List<String> entryConditions, List<String> types) {
        List<Long> topPlaceIds = placeVectorRepository.findTop10SimilarPlaceIdsByRegionAndEmbedding(queryEmbedding, address.getCity().getName(), address.getDistrict(), address.getTown());
        List<PlaceCommand> places = placeRepository.findByVectorSearchAndQueryDsl(topPlaceIds, userLocation, category, size, entryConditions, types);
        places.sort(Comparator.comparingInt(place -> topPlaceIds.indexOf(place.getId()))); // 유사도 순으로 정렬

        return places;
    }

    public List<PlaceCommand> searchByEmbeddingOnly(float[] queryEmbedding, Point userLocation, String category, Size size, List<String> entryConditions, List<String> types) {
        List<Long> topPlaceIds = placeVectorRepository.getTopPlaces(queryEmbedding);
        List<PlaceCommand> places =  placeRepository.findByVectorSearchAndQueryDsl(topPlaceIds, userLocation, category, size, entryConditions, types);
        places.sort(Comparator.comparingInt(place -> topPlaceIds.indexOf(place.getId()))); // 유사도 순으로 정렬

        return places;
    }
}
