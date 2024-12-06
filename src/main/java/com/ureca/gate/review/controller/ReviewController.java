package com.ureca.gate.review.controller;

import com.ureca.gate.global.dto.response.SuccessResponse;
import com.ureca.gate.review.controller.inputport.ReviewService;
import com.ureca.gate.review.controller.inputport.ReviewKeywordService;
import com.ureca.gate.review.controller.inputport.KeywordService;
import com.ureca.gate.review.controller.inputport.ReviewSummaryService;
import com.ureca.gate.review.controller.request.ReviewSaveRequest;
import com.ureca.gate.review.controller.request.ReviewSummaryRequest;
import com.ureca.gate.review.controller.request.ReviewSummarySearchRequest;
import com.ureca.gate.review.controller.response.KeywordResponse;
import com.ureca.gate.review.controller.response.PlaceReviewResponse;
import com.ureca.gate.review.controller.response.ReviewResponse;
import com.ureca.gate.review.controller.response.ReviewSummaryResponse;
import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.domain.enumeration.ReviewSummaryType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Review API", description = "리뷰 API")
@RequestMapping("/api/v1/reviews")
@RestController
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;
  private final KeywordService keywordService;
  private final ReviewKeywordService reviewKeywordService;
  private final ReviewSummaryService reviewSummaryService;

  @GetMapping("/category")
  @Operation(summary = "리뷰 태그 조회 API", description = "장소 카테고리에 맞는 리뷰 태그 조회")
  public SuccessResponse<List<KeywordResponse>> keyword(@RequestParam Long placeId) {
    List<KeywordResponse> response = keywordService.getKeywords(placeId).stream().map(KeywordResponse::from).toList();
    return SuccessResponse.success(response);
  }

  @GetMapping("")
  @Operation(summary = "장소 리뷰 리스트 조회 API", description = "장소에 등록된 리뷰 리스트 조회")
  public SuccessResponse<PlaceReviewResponse> getAll(@RequestParam Long placeId) {
    PlaceReviewResponse response = reviewService.getAllByPlace(placeId);
    return SuccessResponse.success(response);
  }

  @GetMapping("/my")
  @Operation(summary = "나의 리뷰 리스트 API", description = "내가 작성한 리뷰 목록을 조회")
  public SuccessResponse<List<ReviewResponse>> getMyReview(@AuthenticationPrincipal Long memberId) {
    List<ReviewResponse> response = reviewService.getAllByMember(memberId);
    return SuccessResponse.success(response);
  }

  @GetMapping("/{reviewId}")
  @Operation(summary = "리뷰 단건 조회 API", description = "특정 후기 단건 조회")
  public SuccessResponse<ReviewResponse> getReview(@PathVariable Long reviewId) {
    Review review = reviewService.getById(reviewId);
    List<String> fileUrlList = reviewService.getFileUrlList(review.getMember().getId(), review.getReviewFiles());
    List<String> keywordList = reviewKeywordService.getReviewKeywordContents(review);
    ReviewResponse response = ReviewResponse.from(review, fileUrlList, keywordList);
    return SuccessResponse.success(response);
  }

  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "리뷰 작성 API", description = "사진, 영상, 텍스트를 포함한 리뷰 작성")
  public SuccessResponse<ReviewResponse> create(@AuthenticationPrincipal Long memberId, @RequestPart
      ReviewSaveRequest request, @RequestPart(required = false) List<MultipartFile> files)
      throws IOException {
    ReviewResponse response = reviewService.create(memberId, request, files);
    return SuccessResponse.success(response);
  }

  @PutMapping(value = "/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "리뷰 수정 API", description = "사진, 영상 텍스트를 포함한 리뷰 수정")
  public SuccessResponse<ReviewResponse> update(@AuthenticationPrincipal Long memberId, @PathVariable Long reviewId, @RequestPart ReviewSaveRequest request, @RequestPart(required = false) List<MultipartFile> files)
      throws IOException {
    Review review = reviewService.update(memberId, reviewId, request, files);
    List<String> fileUrlList = reviewService.getFileUrlList(memberId, review.getReviewFiles());
    List<String> keywordList = reviewKeywordService.getReviewKeywordContents(review);
    return SuccessResponse.success(ReviewResponse.from(review, fileUrlList, keywordList));
  }

  @DeleteMapping("/{reviewId}")
  @Operation(summary = "리뷰 삭제 API", description = "사진, 영상 텍스트를 포함한 리뷰 삭제")
  public SuccessResponse<Object> delete(@AuthenticationPrincipal Long memberId, @PathVariable Long reviewId) {
    reviewService.delete(reviewId);
    return SuccessResponse.successWithoutResult(null);
  }

  @PostMapping(value = "/summary")
  @Operation(summary = "리뷰 요약 API", description = "리뷰 요약")
  public SuccessResponse<ReviewSummaryResponse> createReviewSummary(@RequestBody ReviewSummaryRequest request){
    ReviewSummaryResponse response = reviewSummaryService.createReviewSummary(request.getDomain(),request.getReviews(),request.getPlaceId());
    return SuccessResponse.success(response);
  }

  @GetMapping(value = "/summary")
  @Operation(summary = "리뷰 요약 조회 API", description = "리뷰 요약 조회 ")
  public SuccessResponse<ReviewSummaryResponse> getReviewSummary(@RequestParam("type") ReviewSummaryType type,
                                                                 @RequestBody ReviewSummarySearchRequest request){
    ReviewSummaryResponse response = reviewSummaryService.getReviewSummary(request.getPlaceId(),type);
    return SuccessResponse.success(response);
  }
}
