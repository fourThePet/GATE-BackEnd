package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.place.application.outputport.PlaceVectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PlaceVectorRepositoryImpl implements PlaceVectorRepository {

    private final PlaceVectorJpaRepository placeVectorJpaRepository;
    @Override
    public List<Long> getTopPlaces(float[] embed) {
        return placeVectorJpaRepository.findTop10SimilarPlaceIds(embed);
    }

    @Override
    public List<Long> findTop10SimilarPlaceIdsByRegionAndEmbedding(float[] embed, String city, String district, String town) {
        return placeVectorJpaRepository.findTop10SimilarPlaceIdsByRegionAndEmbedding(embed,city,district,town);
    }
}
