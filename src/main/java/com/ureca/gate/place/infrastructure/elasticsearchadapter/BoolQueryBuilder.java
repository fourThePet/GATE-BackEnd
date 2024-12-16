package com.ureca.gate.place.infrastructure.elasticsearchadapter;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoolQueryBuilder {
    public SearchRequest searchPlacesQuery(String query, String category, String city) {
        // "should" 조건을 추가할 리스트 생성
        List<Query> shouldQueries = new ArrayList<>();

        // query가 null이 아니고 비어있지 않으면 "should" 조건(nameKeyword, name)에 해당하는 쿼리들 추가
        if (query != null && !query.isEmpty()) {
            shouldQueries.add(new TermQuery.Builder().field("nameKeyword").value(query).boost(2F).build()._toQuery());
            shouldQueries.add(new MatchQuery.Builder().field("name").query(query).operator(Operator.Or).boost(1F).fuzziness("AUTO").build()._toQuery());
        }
        // "filter" 조건(category, city)에 해당하는 Term 쿼리들 추가
        List<Query> filterQueries = new ArrayList<>();

        // category가 null이 아니면 필터 조건에 추가
        if (category != null) {
            filterQueries.add(new TermQuery.Builder().field("category").value(category).build()._toQuery());
        }

        // city가 null이 아니면 필터 조건에 추가
        if (city != null) {
            filterQueries.add(new TermQuery.Builder().field("city").value(city).build()._toQuery());
        }

        // "must_not" 조건으로 제외할 카테고리 추가
        List<Query> mustNotQueries = new ArrayList<>();
        mustNotQueries.add(new TermQuery.Builder().field("category").value("의료").build()._toQuery());
        mustNotQueries.add(new TermQuery.Builder().field("category").value("미용").build()._toQuery());
        mustNotQueries.add(new TermQuery.Builder().field("category").value("반려동물용품").build()._toQuery());


        // BoolQuery 생성
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        // query가 있을 경우에만 "should" 조건을 추가
        if (!shouldQueries.isEmpty()) {
            boolQueryBuilder.should(shouldQueries);
        }

        // "filter" 조건 설정 (category나 city가 null이 아니면 필터 조건을 설정)
        if (!filterQueries.isEmpty()) {
            boolQueryBuilder.filter(filterQueries);
        }
        // "must_not" 조건 설정 (해당 카테고리 제외)
        boolQueryBuilder.mustNot(mustNotQueries);


        // BoolQuery 빌드
        BoolQuery boolQuery = boolQueryBuilder.build();

        // FunctionScoreQuery 생성 (min_score 추가를 조건부로 설정)
        FunctionScoreQuery.Builder functionScoreQueryBuilder = new FunctionScoreQuery.Builder()
                .query(boolQuery._toQuery());  // 기존 BoolQuery 설정

        // query가 null이 아니면 minScore를 추가
        if (query != null && !query.isEmpty()) {
            functionScoreQueryBuilder.minScore(0.1);  // min_score 설정
        }

        // SearchRequest 생성
        return new SearchRequest.Builder()
                .query(functionScoreQueryBuilder.build()._toQuery())  // function_score 쿼리 설정
                .build();


    }
}



