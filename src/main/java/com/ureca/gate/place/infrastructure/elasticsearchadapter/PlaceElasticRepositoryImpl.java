package com.ureca.gate.place.infrastructure.elasticsearchadapter;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
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

        return CustomSlice.convert(placeElasticCustomSlice,PlaceElastic::toModel);
    }

//    @Override
//    public CustomPage<PlaceSearchCommand> findSimilarPlacesByLocation(Double latitude, Double longitude, String query, String category, String city, String district, String town, Size size, List<String> entryConditions, List<String> types, Pageable pageable) {
//
//        SearchRequest elasticSearchQuery = boolQueryBuilders.searchPlacesQuery(latitude, longitude, query, category, city, district, town, size.name(), entryConditions, types) ;
//        System.out.println("Generated Query: " + elasticSearchQuery.query());  // 쿼리 확인
//        System.out.println("sort:" + elasticSearchQuery.sort());
//
//        List<PlaceSearchCommand> placeResponses = new ArrayList<>();
//
//        try {
//            // Elasticsearch 검색 실행
//            SearchResponse<JsonData> response = elasticsearchClient.search(s -> s
//                            .index("placesss")  // 인덱스명 설정
//                            .query(elasticSearchQuery.query())
//                            .scriptFields(elasticSearchQuery.scriptFields())
//                            .source(source -> source.fetch(true)) // _source 반환 활성화
//                            .sort(elasticSearchQuery.sort())
//                            .from(0).size(30), // 페이징 처리
//                    JsonData.class);
//
//            /// 결과 처리
//            for (Hit<JsonData> hit : response.hits().hits()) {
//                Map<String, JsonData> fields = hit.fields();
//                Double distance = null;
//
//                // distance 값 추출
//                if (fields.containsKey("distance")) {
//                    List<Object> distanceValues = fields.get("distance").to(List.class);
//                    if (!distanceValues.isEmpty()) {
//                        distance = ((Number) distanceValues.get(0)).doubleValue();
//                    }
//                }
//
//                // Source 변환
//                JsonData source = hit.source();
//                if (source != null) {
//                    PlaceElastic place = source.to(PlaceElastic.class); // PlaceElastic로 변환
//                    PlaceSearchCommand command = PlaceSearchCommand.from(Long.valueOf(hit.id()),place, distance); // distance 포함
//                    placeResponses.add(command);
//                }
//            }
//            long totalHits = response.hits().total().value();
//
//            // 페이지 객체 생성
//            Page<PlaceSearchCommand> page = new PageImpl<>(placeResponses, pageable, totalHits);
//            return CustomPage.from(page);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    //프론트가 분리하기 힘들어 합친 코드
    @Override
    public List<PlaceSearchCommand> findSimilarPlacesByLocation(Double latitude, Double longitude, String query, String category, String city, String district, String town, Size size, List<String> entryConditions, List<String> types) {
        if(size == null){
            size = Size.LARGE;
        }
        SearchRequest elasticSearchQuery = boolQueryBuilders.searchPlacesQuery(latitude, longitude, query, category, city, district, town, size.name(), entryConditions, types) ;
        System.out.println("Generated Query: " + elasticSearchQuery.query());  // 쿼리 확인
        System.out.println("sort:" + elasticSearchQuery.sort());

        List<PlaceSearchCommand> placeResponses = new ArrayList<>();

        try {
            // Elasticsearch 검색 실행
            SearchResponse<JsonData> response = elasticsearchClient.search(s -> s
                            .index("placessss")  // 인덱스명 설정
                            .query(elasticSearchQuery.query())
                            .scriptFields(elasticSearchQuery.scriptFields())
                            .source(source -> source.fetch(true)) // _source 반환 활성화
                            .sort(elasticSearchQuery.sort())
                            .from(0).size(30), // 페이징 처리
                    JsonData.class);

            /// 결과 처리
            for (Hit<JsonData> hit : response.hits().hits()) {
                Map<String, JsonData> fields = hit.fields();
                Double distance = null;

                // distance 값 추출
                if (fields.containsKey("distance")) {
                    List<Object> distanceValues = fields.get("distance").to(List.class);
                    if (!distanceValues.isEmpty()) {
                        distance = ((Number) distanceValues.get(0)).doubleValue();
                    }
                }

                // Source 변환
                JsonData source = hit.source();
                if (source != null) {
                    PlaceElastic place = source.to(PlaceElastic.class); // PlaceElastic로 변환
                    PlaceSearchCommand command = PlaceSearchCommand.from(Long.valueOf(hit.id()),place, distance); // distance 포함
                    placeResponses.add(command);
                }
            }

            // 페이지 객체 생성
            return placeResponses;

        } catch (IOException e) {
            throw new RuntimeException(e);
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

}
