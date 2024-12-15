package com.ureca.gate.place.application;

import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.place.application.outputport.PlaceElasticRepository;
import com.ureca.gate.place.controller.inputport.PlaceService;
import com.ureca.gate.place.domain.SearchPlace;
import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.domain.SearchReview;
import com.ureca.gate.review.domain.enumeration.ReviewAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.PLACE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceElasticRepository placeElasticRepository;

    @Transactional
    @Override
    public void addReviewAndUpdateAvgRating(Review savedReview) {
        // 리뷰 추가 후 평균 별점 업데이트
        processReview(savedReview, ReviewAction.ADD);
    }

    @Transactional
    @Override
    public void updateReviewAndRecalculateAvgRating(Review updatedReview) {
        // 리뷰 수정 후 평균 별점 업데이트
        processReview(updatedReview, ReviewAction.UPDATE);
    }

    @Transactional
    @Override
    public void deleteReviewAndRecalculateAvgRating(Review review) {
        // 리뷰 삭제 후 평균 별점 업데이트
        processReview(review, ReviewAction.DELETE);
    }

    // 공통된 처리 로직을 하나의 메소드로 추상화
    private void processReview(Review review, ReviewAction action) {

        SearchPlace searchPlace = placeElasticRepository.findById(review.getPlace().getId())
                .orElseThrow(() -> new BusinessException(PLACE_NOT_FOUND));

        //리뷰 추가, 수정, 삭제 처리
        switch (action) {
            case ADD:
                searchPlace.addReview(SearchReview.create(review.getId(), review.getStarRate())); // 리뷰 추가
                break;
            case UPDATE:
                searchPlace.updateReviewStarRate(review); // 리뷰 수정
                break;
            case DELETE:
                searchPlace.removeReview(review.getId()); // 리뷰 삭제
                break;
        }

        searchPlace.updateStarAvg();

        placeElasticRepository.update(searchPlace);
    }


}


