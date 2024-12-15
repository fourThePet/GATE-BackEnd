package com.ureca.gate.place.domain;

import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.domain.SearchReview;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.REVIEW_NOT_FOUND;

@Getter
public class SearchPlace {
    private final Long id;
    private final String placeName;
    private final Double latitude;
    private final Double longitude;
    private final String roadAddress;
    private final String photoUrl;
    private List<SearchReview> reviews;
    private Double starAvg;

    @Builder
    public SearchPlace(Long id, String placeName, Double latitude, Double longitude, String roadAddress, String photoUrl, List<SearchReview> reviews, Double starAvg) {
        this.id = id;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.roadAddress = roadAddress;
        this.photoUrl = photoUrl;
        this.reviews = reviews;
        this.starAvg = starAvg;
    }

    // 리뷰 업데이트 메서드
    public void updateReviewStarRate(Review updatedReview) {
        // 기존 리뷰 찾기
        SearchReview existingReview = reviews.stream()
                .filter(review -> review.getId().equals(updatedReview.getId()))
                .findFirst()
                .orElseThrow(() -> new BusinessException(REVIEW_NOT_FOUND));

        // 기존 리뷰의 별점을 수정된 별점으로 변경
        existingReview.modifyStarRate(updatedReview.getStarRate());

    }

    public void removeReview(Long reviewId) {
        this.reviews.removeIf(review -> review.getId().equals(reviewId));
    }
    public void addReview(SearchReview searchReview) {
        this.reviews.add(searchReview);
    }

    // 평균 별점 계산 메서드
    public void updateStarAvg() {
        this.starAvg = reviews.stream()
                .mapToDouble(SearchReview::getStarRate)
                .average()
                .orElse(0.0);
    }


}

