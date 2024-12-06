package com.ureca.gate.review.domain;

import com.ureca.gate.place.domain.Place;
import com.ureca.gate.review.domain.enumeration.ReviewSummaryType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class ReviewSummary {
    private Long id;
    private Place place;
    private String comment;
    private ReviewSummaryType type;
    // 생성자
    @Builder
    public ReviewSummary(Long id, Place place, ReviewSummaryType type, String comment) {
        this.id = id; // ID는 데이터베이스에서 자동 생성
        this.place = place;
        this.type = type;
        this.comment = comment;
    }

    public static List<ReviewSummary> createFromParsedSummary(Place place, Map<ReviewSummaryType, String> parsedSummary) {
        return parsedSummary.entrySet().stream()
                .map(entry -> ReviewSummary.builder()
                        .place(place)
                        .type(entry.getKey())
                        .comment(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}
