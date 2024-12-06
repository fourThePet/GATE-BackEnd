package com.ureca.gate.review.domain;

import com.ureca.gate.review.domain.enumeration.ReviewSummaryType;

import java.util.HashMap;
import java.util.Map;

public class ReviewSummaryParser {
    public static Map<ReviewSummaryType, String> parse(String summary) {
        Map<ReviewSummaryType, String> result = new HashMap<>();

        // 요약 데이터를 줄 단위로 나누고 각 키워드로 매칭
        String[] sections = summary.split("\n\n");
        for (String section : sections) {
            if (section.startsWith("장점")) {
                result.put(ReviewSummaryType.POSITIVE, section.replace("장점 : ", "").trim());
            } else if (section.startsWith("단점")) {
                result.put(ReviewSummaryType.NEGATIVE, section.replace("단점 : ", "").trim());
            } else if (section.startsWith("종합")) {
                result.put(ReviewSummaryType.OVERALL, section.replace("종합 : ", "").trim());
            }
        }
        return result;
    }
}
