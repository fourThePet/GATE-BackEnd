package com.ureca.gate.place.infrastructure.elasticsearchadapter;


import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.domain.CustomPage;
import com.ureca.gate.place.infrastructure.command.PlaceSearchCommand;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.*;
import com.ureca.gate.global.domain.CustomSlice;
import com.ureca.gate.place.application.outputport.PlaceElasticRepository;
import com.ureca.gate.place.domain.SearchPlace;
import com.ureca.gate.place.infrastructure.elasticsearchadapter.Document.PlaceElastic;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PlaceElasticRepositoryImpl implements PlaceElasticRepository {
    private final PlaceElasticSearchRepository placeElasticSearchRepository;
    private final BoolQueryBuilders boolQueryBuilders;
    private final BoolQueryBuilder boolQueryBuilder;
    private final ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public CustomSlice<SearchPlace> findByQueryAndCategoryAndCity(String query, String city, String category, Pageable pageable) {

        SearchRequest elasticSearchQuery = boolQueryBuilder.searchPlacesQuery(query, category, city);
        System.out.println("Generated Query: " + elasticSearchQuery.query());  // 쿼리 확인

        NativeQuery searchQuery = new NativeQuery(elasticSearchQuery.query());
        searchQuery.setPageable(pageable);
        SearchHits<PlaceElastic> searchHits = elasticsearchTemplate.search(searchQuery, PlaceElastic.class);

        List<PlaceElastic> placeElastics = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)  // SearchHit에서 content (PlaceElastic 객체) 추출
                .collect(Collectors.toList());

        // hasNext를 확인하여 Slice 객체 생성
        boolean hasNext = searchHits.getTotalHits() > (long) (pageable.getPageNumber() + 1) * pageable.getPageSize();

        // Slice 객체 생성 (hasNext를 통해 다음 페이지 여부 확인)
        Slice<PlaceElastic> slice = new SliceImpl<>(placeElastics, pageable, hasNext);
        CustomSlice<PlaceElastic> placeElasticCustomSlice = CustomSlice.from(slice);

        return CustomSlice.convert(placeElasticCustomSlice,PlaceElastic::toModel);
    }

    @Override
    public CustomPage<PlaceSearchCommand> findSimilarPlacesByLocation(Double latitude, Double longitude, String query, String category, String city, String district, String town, Size size, List<String> entryConditions, List<String> types, Pageable pageable) {

        SearchRequest elasticSearchQuery = boolQueryBuilders.searchPlacesQuery(latitude, longitude, query, category, city, district, town, size.name(), entryConditions, types) ;
        System.out.println("Generated Query: " + elasticSearchQuery.query());  // 쿼리 확인
        System.out.println("sort:" + elasticSearchQuery.sort());

        NativeQuery searchQuery = new NativeQuery(elasticSearchQuery.query());
        searchQuery.setPageable(pageable);

        SearchHits<PlaceElastic> searchHits = elasticsearchTemplate.search(searchQuery, PlaceElastic.class);

        System.out.println("response: " + searchHits.getSearchHits());  // 쿼리 확인

        // SearchHit에서 PlaceElastic 및 sort 값을 추출
        List<PlaceSearchCommand> placeResponses = searchHits.getSearchHits().stream()
                .map(hit -> {
                    PlaceElastic place = hit.getContent();
                    return PlaceSearchCommand.from(place);
                })
                .toList();

        long totalHits = searchHits.getTotalHits();

        Page<PlaceSearchCommand> page = new PageImpl<>(placeResponses, pageable, totalHits);

        return CustomPage.from(page);
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
