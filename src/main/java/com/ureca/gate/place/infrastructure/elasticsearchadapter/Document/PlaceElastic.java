package com.ureca.gate.place.infrastructure.elasticsearchadapter.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ureca.gate.place.domain.SearchPlace;
import com.ureca.gate.review.domain.SearchReview;
import com.ureca.gate.review.infrasturcture.elasticsearchadapter.Document.ReviewElastic;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;
import java.util.stream.Collectors;

@Document(indexName = "placessss")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
public class PlaceElastic {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String name;  // 장소 이름

    @Field(type = FieldType.Keyword)
    private String nameKeyword;  // 장소 이름

    @Field(type = FieldType.Text)
    private String category;  // 카테고리

    @Field(type = FieldType.Keyword)
    private String categoryKeyword;

    @Field(type = FieldType.Keyword)
    private String planCategory;

    @Field(type = FieldType.Keyword)
    private String city;  // 도시

    @Field(type = FieldType.Keyword)
    private String district;

    @Field(type = FieldType.Keyword)
    private String town;

    private String roadAddress;

    @Field(type = FieldType.Object)
    private GeoPoint location;

    private String profileUrl;  // 프로필 URL 추가

    @Field(type = FieldType.Nested)  // Nested 타입으로 리뷰 필드 정의
    private List<ReviewElastic> reviews;  // 리뷰 목록

    @Field(type = FieldType.Double)
    private Double starAvg;

    @Field(type = FieldType.Keyword)
    private String size;  // 입마개 필수 여부

    @Field(type = FieldType.Keyword)
    private String isLeashRequired;

    @Field(type = FieldType.Keyword)
    private String isMuzzleRequired;  // 입마개 필수 여부

    @Field(type = FieldType.Keyword)
    private String isCageRequired;  // 케이지 필수 여부

    @Field(type = FieldType.Keyword)
    private String isVaccinationComplete;  // 예방 접종 완료 여부

    @Field(type = FieldType.Keyword)
    private String outdoorAvailable;  // 실외 가능 여부

    @Field(type = FieldType.Keyword)
    private String indoorAvailable;  // 실내 가능 여부

    @Field(type = FieldType.Keyword)
    private String parkingAvailable;  // 주차 가능 여부

    public void update(SearchPlace searchPlace){
        this.reviews = searchPlace.getReviews().stream()
                .map(ReviewElastic::from)  // 리뷰 객체 변환
                .collect(Collectors.toList());
        this.starAvg = searchPlace.getStarAvg();
    }

    public SearchPlace toModel() {
        List<SearchReview> searchReviews = reviews.stream()
                .map(ReviewElastic::toModel)  // ReviewElastic을 SearchReview로 변환
                .collect(Collectors.toList());

        return SearchPlace.builder()
                .id(id)
                .placeName(name)
                .latitude(location.getLat())
                .longitude(location.getLon())
                .roadAddress(roadAddress)
                .photoUrl(profileUrl)
                .reviews(searchReviews)  // 변환된 SearchReview 리스트를 설정
                .starAvg(starAvg)
                .build();
    }
}

