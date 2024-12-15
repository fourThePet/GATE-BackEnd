package com.ureca.gate.place.infrastructure.elasticsearchadapter;


import com.ureca.gate.global.domain.CustomSlice;
import com.ureca.gate.place.application.outputport.PlaceElasticRepository;
import com.ureca.gate.place.domain.SearchPlace;
import com.ureca.gate.place.infrastructure.elasticsearchadapter.Document.PlaceElastic;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlaceElasticRepositoryImpl implements PlaceElasticRepository {
    private final PlaceElasticSearchRepository placeElasticSearchRepository;

    @Override
    public CustomSlice<SearchPlace> findByQueryAndCategoryAndCity(String query, String city, String category, Pageable pageable) {
        Page<PlaceElastic> placeElasticPage = placeElasticSearchRepository.findByQueryAndCategoryAndCity(query,city,category,pageable);
        CustomSlice<PlaceElastic> placeElasticCustomSlice = CustomSlice.from(placeElasticPage);
        return CustomSlice.convert(placeElasticCustomSlice,PlaceElastic::toModel);
    }

    @Override
    public CustomSlice<SearchPlace> findByCategoryAndCity(String category, String city, Pageable pageable) {
        Page<PlaceElastic> placeElasticPage =  placeElasticSearchRepository.findByCategoryAndCity(category,city,pageable);
        CustomSlice<PlaceElastic> placeElasticCustomSlice = CustomSlice.from(placeElasticPage);
        return CustomSlice.convert(placeElasticCustomSlice,PlaceElastic::toModel);
    }

    @Override
    public Optional<SearchPlace> findById(Long placeElasticId) {
        return placeElasticSearchRepository.findById(placeElasticId).map(PlaceElastic::toModel);
    }

    @Override
    public SearchPlace update(SearchPlace searchPlace) {
        PlaceElastic placeElastic =placeElasticSearchRepository.findById(searchPlace.getId()).get();
        placeElastic.update(searchPlace);
        return placeElasticSearchRepository.save(placeElastic).toModel();
    }


}
