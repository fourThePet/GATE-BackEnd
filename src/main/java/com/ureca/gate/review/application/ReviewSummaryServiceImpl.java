package com.ureca.gate.review.application;

import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.review.application.outputport.GptService;
import com.ureca.gate.review.application.outputport.ReviewSummaryRepository;
import com.ureca.gate.review.controller.inputport.ReviewSummaryService;
import com.ureca.gate.review.controller.response.ReviewSummaryResponse;
import com.ureca.gate.review.domain.ReviewSummary;
import com.ureca.gate.review.domain.ReviewSummaryParser;
import com.ureca.gate.review.domain.enumeration.ReviewSummaryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.PLACE_NOT_FOUND;
import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.REVIEW_SUMMARY_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewSummaryServiceImpl implements ReviewSummaryService {
    private final GptService gptService;
    private final ReviewSummaryRepository reviewSummaryRepository;
    private final PlaceRepository placeRepository;
    @Override
    @Transactional
    public ReviewSummaryResponse createReviewSummary(String domain, String reviews, Long placeId) {
         String summary = gptService.getGptSummary(domain,reviews);

        // 요약 데이터를 파싱
        Map<ReviewSummaryType, String> parsedSummary = ReviewSummaryParser.parse(summary);

        // Place 조회
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new BusinessException(PLACE_NOT_FOUND));

        // ReviewSummary 도메인 객체 생성 및 저장
        ReviewSummary.createFromParsedSummary(place, parsedSummary)
                .forEach(reviewSummaryRepository::save);

        return ReviewSummaryResponse.from(summary);
    }

    @Override
    public ReviewSummaryResponse getReviewSummary(Long placeId,ReviewSummaryType type) {
        ReviewSummary reviewSummary = reviewSummaryRepository.findByPlaceIdAndType(placeId,type).orElseThrow(()->new BusinessException(REVIEW_SUMMARY_NOT_FOUND));
        return ReviewSummaryResponse.from(reviewSummary.getComment());
    }
}
