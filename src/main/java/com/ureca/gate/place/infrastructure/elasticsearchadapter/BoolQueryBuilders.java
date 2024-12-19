package com.ureca.gate.place.infrastructure.elasticsearchadapter;

import co.elastic.clients.elasticsearch._types.*;
import co.elastic.clients.elasticsearch._types.query_dsl.*;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.json.JsonData;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BoolQueryBuilders {

    public SearchRequest searchPlacesQuery(Double latitude, Double longitude, String query, String category,
                                           String city, String district, String town, String size,
                                           List<String> entryConditions, List<String> types) {

        // BoolQuery 생성
        BoolQuery.Builder boolQueryBuilder = buildBoolQuery(query, category, city, district, town, size, entryConditions, types);


        FunctionScoreQuery.Builder functionScoreQueryBuilder = buildFunctionScoreQuery(latitude, longitude, boolQueryBuilder, city, district, town);

        // script_fields로 거리 계산 추가
        ScriptField distanceScriptField = new ScriptField.Builder()
                .script(new Script.Builder()
                        .inline(s -> s
                                .source("doc['location'].arcDistance(params.lat, params.lon)")
                                .params(Map.of(
                                        "lat", JsonData.of(latitude),
                                        "lon", JsonData.of(longitude)
                                ))) // Map에 파라미터 추가
                        .build())
                .ignoreFailure(false) // 실패 시 무시하지 않음
                .build();

        // script_fields에 name과 ScriptField를 매핑합니다.
        Map<String, ScriptField> scriptFields = new HashMap<>();
        scriptFields.put("distance", distanceScriptField);

        // 지역명이 없는 경우: 스코어 정렬
        if (isNullOrEmpty(city) && isNullOrEmpty(district) && isNullOrEmpty(town)) {
            return new SearchRequest.Builder()
                    .query(functionScoreQueryBuilder.build()._toQuery()) // 가우스 함수 적용
                    .scriptFields(scriptFields) // script_fields로 거리 추가
                    .sort(SortOptions.of(s -> s.score(ScoreSort.of(ss -> ss.order(SortOrder.Desc))))) // 스코어 정렬
                    .build();
        }

        // 지역명이 있는 경우: 거리 정렬 추가
        return new SearchRequest.Builder()
                .query(functionScoreQueryBuilder.build()._toQuery()) // FunctionScoreQuery 적용
                .scriptFields(scriptFields) // script_fields로 거리 추가
                .sort(buildSortOptions(latitude, longitude)) // 거리 정렬
                .build();
    }

    // =========================== Private Methods ================================= //

    // Bool Query 빌드 메서드
    private BoolQuery.Builder buildBoolQuery(String query, String category, String city,
                                             String district, String town, String size,
                                             List<String> entryConditions, List<String> types) {
        List<Query> shouldQueries = new ArrayList<>();
        List<Query> filterQueries = new ArrayList<>();

        // Query 추가
        if (isNotEmpty(query)) {
            shouldQueries.add(buildTermQuery("nameKeyword", query, 3.0f));
            shouldQueries.add(buildMatchQuery("name", query, 1.0f));
            shouldQueries.add(buildMatchQuery("category", query, 1.0f));
        }

        // Filter 추가
        addFilterIfNotNull(filterQueries, "categoryKeyword", category);
        addFilterIfNotNull(filterQueries, "city", city);
        addFilterIfNotNull(filterQueries, "district", district);
        addFilterIfNotNull(filterQueries, "town", town);

        // size 필터 처리
        if (isNotEmpty(size)) {
            filterQueries.add(buildSizeFilter(size));
        }

        // 조건 필터 추가
        if (entryConditions != null) {
            entryConditions.forEach(condition -> addFilterIfNotNull(filterQueries, condition, "Y"));
        }

        // types 필터 추가 (값이 "Y"인 경우)
        if (types != null) {
            types.forEach(type -> addFilterIfNotNull(filterQueries, type, "Y"));
        }

        return new BoolQuery.Builder()
                .should(shouldQueries)
                .filter(filterQueries);
    }

    // size 필터링 처리 메서드
    private Query buildSizeFilter(String size) {
        List<String> allowedSizes = new ArrayList<>();

        switch (size.toUpperCase()) {
            case "SMALL":
                // SMALL → SMALL, MEDIUM, LARGE 모두 허용
                allowedSizes.add("SMALL");
                allowedSizes.add("MEDIUM");
                allowedSizes.add("LARGE");
                break;

            case "MEDIUM":
                // MEDIUM → MEDIUM, LARGE 허용 (SMALL은 제외)
                allowedSizes.add("MEDIUM");
                allowedSizes.add("LARGE");
                break;

            case "LARGE":
                // LARGE → LARGE만 허용
                allowedSizes.add("LARGE");
                break;
        }

        // 필터링 조건을 TermsQuery로 반환
        return new TermsQuery.Builder()
                .field("size")
                .terms(t -> t.value(allowedSizes.stream()
                        .map(FieldValue::of)
                        .toList()))
                .build()
                ._toQuery();
    }



    private FunctionScoreQuery.Builder buildFunctionScoreQuery(Double latitude, Double longitude,
                                                               BoolQuery.Builder boolQueryBuilder,
                                                               String city, String district, String town) {
        FunctionScoreQuery.Builder functionScoreQueryBuilder = new FunctionScoreQuery.Builder()
                .query(boolQueryBuilder.build()._toQuery());

        // query가 null이 아니면 minScore 설정
        functionScoreQueryBuilder.minScore(0.1);


        // 지역명이 없을 경우: 가우스 함수 적용
        if (isNullOrEmpty(city) && isNullOrEmpty(district) && isNullOrEmpty(town)) {
            DecayPlacement placement = new DecayPlacement.Builder()
                    .origin(JsonData.of(latitude + "," + longitude))
                    .scale(JsonData.of("10km"))
                    .offset(JsonData.of("0.5km"))
                    .decay(0.5)
                    .build();

            DecayFunction decayFunction = new DecayFunction.Builder()
                    .field("location")
                    .placement(placement)
                    .build();

            functionScoreQueryBuilder.functions(f -> f.gauss(decayFunction).weight(2.0))
                    .scoreMode(FunctionScoreMode.Sum)
                    .boostMode(FunctionBoostMode.Multiply);
        }

        return functionScoreQueryBuilder;
    }


    // 정렬 조건 생성
    private List<SortOptions> buildSortOptions(Double latitude, Double longitude) {
        List<SortOptions> sortOptions = new ArrayList<>();
        sortOptions.add(SortOptions.of(s -> s.score(ScoreSort.of(ss -> ss.order(SortOrder.Desc)))));

        // GeoDistance 정렬
        sortOptions.add(SortOptions.of(s -> s.geoDistance(gd -> gd
                .field("location") // location 필드
                .location(l -> l.latlon(latlon -> latlon
                        .lat(latitude)
                        .lon(longitude)))
                .order(SortOrder.Asc) // 오름차순
                .unit(DistanceUnit.Kilometers) // 단위: km
                .mode(SortMode.Min)))); // 최소 거리 기준

        return sortOptions;
    }

    // =========================== Utility Methods ================================= //

    private boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    private void addFilterIfNotNull(List<Query> filterQueries, String field, String value) {
        if (isNotEmpty(value)) {
            filterQueries.add(buildTermQuery(field, value, null));
        }
    }

    private Query buildTermQuery(String field, String value, Float boost) {
        TermQuery.Builder termQuery = new TermQuery.Builder().field(field).value(value);
        if (boost != null) {
            termQuery.boost(boost);
        }
        return termQuery.build()._toQuery();
    }

    private Query buildMatchQuery(String field, String value, Float boost) {
        return new MatchQuery.Builder()
                .field(field)
                .query(value)
                .operator(Operator.Or)
                .boost(boost)
                .fuzziness("AUTO")
                .build()
                ._toQuery();
    }
    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
