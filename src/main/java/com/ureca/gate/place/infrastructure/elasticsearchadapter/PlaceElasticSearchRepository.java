package com.ureca.gate.place.infrastructure.elasticsearchadapter;

import com.ureca.gate.place.infrastructure.elasticsearchadapter.Document.PlaceElastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PlaceElasticSearchRepository extends ElasticsearchRepository<PlaceElastic, Long> {

}

