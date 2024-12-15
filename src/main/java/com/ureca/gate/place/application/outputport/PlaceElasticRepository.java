package com.ureca.gate.place.application.outputport;

import com.ureca.gate.global.domain.CustomSlice;
import com.ureca.gate.place.domain.SearchPlace;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface PlaceElasticRepository{

    CustomSlice<SearchPlace> findByQueryAndCategoryAndCity(
            String query,
            String city,
            String category,
            Pageable pageable
    );

    CustomSlice<SearchPlace> findByCategoryAndCity(String category, String city, Pageable pageable);

    Optional<SearchPlace> findById(Long placeElasticId);

    SearchPlace update(SearchPlace SearchPlace);
}
