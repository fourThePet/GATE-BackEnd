package com.ureca.gate.review.application.outputport;

public interface GptService {
    String getGptSummary(String domain, String reviews);

    String makeReviewSummary(String answer);

    String getRegion(String query);
}
