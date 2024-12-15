package com.ureca.gate.review.infrasturcture.elasticsearchadapter.Document;

import com.ureca.gate.review.domain.SearchReview;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "reviews")
@NoArgsConstructor
@Getter
public class ReviewElastic {

    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Double)
    private Integer starRate;  // 평균 평점

    public static ReviewElastic from(SearchReview searchReview){
        ReviewElastic reviewElastic = new ReviewElastic();
        reviewElastic.id = searchReview.getId();
        reviewElastic.starRate = searchReview.getStarRate();
        return reviewElastic;
    }

    public SearchReview toModel(){
        return SearchReview.builder()
                .id(id)
                .starRate(starRate)
                .build();
    }
}

