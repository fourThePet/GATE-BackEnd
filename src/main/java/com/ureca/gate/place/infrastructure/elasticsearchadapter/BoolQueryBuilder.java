package com.ureca.gate.place.infrastructure.elasticsearchadapter;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoolQueryBuilder {
    public Query searchPlacesQuery(String query, String category, String city) {
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

        return boolQueryBuilder.build()._toQuery();
    }
}



