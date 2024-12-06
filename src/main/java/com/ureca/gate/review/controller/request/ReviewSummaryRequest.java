package com.ureca.gate.review.controller.request;

import lombok.Getter;

@Getter
public class ReviewSummaryRequest {
    private Long placeId;
    private String domain;
    private String reviews;
}
