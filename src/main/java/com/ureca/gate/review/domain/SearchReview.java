package com.ureca.gate.review.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchReview {

    private Long id;

    private Integer starRate;

    public static SearchReview create(Long id,Integer starRate){
        return SearchReview.builder()
                .id(id)
                .starRate(starRate)
                .build();
    }

    public void modifyStarRate(Integer newStarRate) {
        this.starRate = newStarRate;
    }


}
