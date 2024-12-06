package com.ureca.gate.review.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewSummaryResponse {
    private String answer;

    public static ReviewSummaryResponse from(String question){
        return ReviewSummaryResponse.builder()
                .answer(question)
                .build();
    }
}
