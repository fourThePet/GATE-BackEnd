package com.ureca.gate.place.infrastructure.elasticsearchadapter;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.place.infrastructure.command.PlaceSearchCommand;
import lombok.extern.slf4j.Slf4j;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PlaceElasticRepositoryImpl implements PlaceElasticRepository {
    private final PlaceElasticSearchRepository placeElasticSearchRepository;
    private final BoolQueryBuilders boolQueryBuilders;
    private final BoolQueryBuilder boolQueryBuilder;
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final ElasticsearchClient elasticsearchClient;
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

        return CustomSlice.convert(placeElasticCustomSlice, PlaceElastic::toModel);
    }
    //지도 내 검색
    @Override
    public List<PlaceSearchCommand> findSimilarPlacesByLocation(
            Double latitude, Double longitude, String query, String category,
            String city, String district, String town, Size size,
            List<String> entryConditions, List<String> types) {

        if (size == null) {
            size = Size.LARGE;
        }

        // Elasticsearch 검색 쿼리 생성
        SearchRequest elasticSearchQuery = boolQueryBuilders.searchPlacesQuery(
                latitude, longitude, query, category, city, district, town,
                size.name(), entryConditions, types);

        log.info("Generated Query: {}", elasticSearchQuery.query());
        log.info("Sort: {}", elasticSearchQuery.sort());

        try {
            // Elasticsearch 검색 실행
            SearchResponse<JsonData> response = executeSearchQuery(elasticSearchQuery);
            return extractSearchResults(response);
        } catch (IOException e) {
            throw new RuntimeException("Elasticsearch 검색 중 오류 발생: " + e.getMessage(), e);
        }
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

    /**
     * Elasticsearch 검색 실행 메서드
     */
    private SearchResponse<JsonData> executeSearchQuery(SearchRequest query) throws IOException {
        return elasticsearchClient.search(s -> s
                        .index("placessss")  // 인덱스명 설정
                        .query(query.query())
                        .scriptFields(query.scriptFields())
                        .source(source -> source.fetch(true)) // _source 반환 활성화
                        .sort(query.sort())
                        .from(0).size(30), // 페이징 처리
                JsonData.class);
    }

    /**
     * 검색 결과를 PlaceSearchCommand 리스트로 변환
     */
    private List<PlaceSearchCommand> extractSearchResults(SearchResponse<JsonData> response) {
        List<PlaceSearchCommand> placeResponses = new ArrayList<>();

        for (Hit<JsonData> hit : response.hits().hits()) {
            Double distance = extractDistance(hit.fields());
            JsonData source = hit.source();

            if (source != null) {
                PlaceElastic place = source.to(PlaceElastic.class);
                PlaceSearchCommand command = PlaceSearchCommand.from(Long.valueOf(hit.id()), place, distance);
                placeResponses.add(command);
            }
        }

        return placeResponses;
    }

    /**
     * 검색 결과에서 거리(distance) 값을 추출하는 메서드
     */
    private Double extractDistance(Map<String, JsonData> fields) {
        return Optional.ofNullable(fields.get("distance"))
                .map(jsonData -> jsonData.to(List.class))
                .filter(list -> !list.isEmpty())
                .map(list -> (Number) list.get(0))
                .map(Number::doubleValue)
                .orElse(null);
    }
}
