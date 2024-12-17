package com.ureca.gate.place.application.outputport;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.domain.CustomPage;
import com.ureca.gate.global.domain.CustomSlice;
import com.ureca.gate.place.controller.response.PlaceForMapResponse;
import com.ureca.gate.place.domain.SearchPlace;
import com.ureca.gate.place.infrastructure.command.PlaceSearchCommand;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface PlaceElasticRepository{

    CustomSlice<SearchPlace> findByQueryAndCategoryAndCity(
            String query,
            String city,
            String category,
            Pageable pageable
    );

    CustomPage<PlaceSearchCommand> findSimilarPlacesByLocation(Double latitude, Double longitude, String query, String category, String city, String district, String town, Size size, List<String> entryConditions, List<String> types, Pageable pageable);

    Optional<SearchPlace> findById(Long placeElasticId);

    SearchPlace update(SearchPlace SearchPlace);
}
