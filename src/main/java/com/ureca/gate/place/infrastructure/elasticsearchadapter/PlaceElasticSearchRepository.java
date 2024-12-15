package com.ureca.gate.place.infrastructure.elasticsearchadapter;

import com.ureca.gate.place.infrastructure.elasticsearchadapter.Document.PlaceElastic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PlaceElasticSearchRepository extends ElasticsearchRepository<PlaceElastic, Long> {

    @Query("{\"bool\": {\"should\": [" +
            "{\"term\": {\"nameKeyword\": {\"value\": \"?0\", \"boost\": 2.0}}}, " +
            "{\"match\": {\"name\": {\"query\": \"?0\", \"operator\": \"or\", \"boost\": 1.0, \"fuzziness\": \"AUTO\"}}}" +
            "], \"filter\": [" +
            "{\"term\": {\"category\": \"?1\"}}, " +
            "{\"term\": {\"city\": \"?2\"}}" +
            "]}}}")
    Page<PlaceElastic> findByQueryAndCategoryAndCity(String query, String category, String city, Pageable pageable);

    @Query("{\"bool\": {\"filter\": [" +
            "{\"term\": {\"category\": \"?0\"}}, " +
            "{\"term\": {\"city\": \"?1\"}}" +
            "]}}")
    Page<PlaceElastic> findByCategoryAndCity(String category, String city, Pageable pageable);

}

