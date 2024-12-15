package com.ureca.gate.place.infrastructure.elasticsearchadapter.Document;

import com.ureca.gate.place.domain.SearchPlace;
import com.ureca.gate.review.domain.SearchReview;
import com.ureca.gate.review.infrasturcture.elasticsearchadapter.Document.ReviewElastic;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.stream.Collectors;

@Document(indexName = "places")
@NoArgsConstructor
@Getter
public class PlaceElastic {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String name;  // 장소 이름

    @Field(type = FieldType.Keyword)
    private String nameKeyword;  // 장소 이름

    @Field(type = FieldType.Keyword)
    private String category;  // 카테고리

    @Field(type = FieldType.Keyword)
    private String city;  // 도시

    private String roadAddress;

    private Double latitude;

    private Double longitude;

    private String profileUrl;  // 프로필 URL 추가

    @Field(type = FieldType.Nested)  // Nested 타입으로 리뷰 필드 정의
    private List<ReviewElastic> reviews;  // 리뷰 목록

    private Double starAvg;

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
                .latitude(latitude)
                .longitude(longitude)
                .roadAddress(roadAddress)
                .photoUrl(profileUrl)
                .reviews(searchReviews)  // 변환된 SearchReview 리스트를 설정
                .starAvg(starAvg)
                .build();
    }

}

