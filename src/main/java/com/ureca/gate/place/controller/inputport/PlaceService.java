package com.ureca.gate.place.controller.inputport;

import com.ureca.gate.review.domain.Review;

public interface PlaceService {
    void addReviewAndUpdateAvgRating(Review savedReview);

    void updateReviewAndRecalculateAvgRating(Review updatedReview);

    void deleteReviewAndRecalculateAvgRating(Review deleteReview);
}
