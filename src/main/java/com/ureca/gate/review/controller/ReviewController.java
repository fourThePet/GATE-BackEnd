package com.ureca.gate.review.controller;

import com.ureca.gate.global.dto.response.SuccessResponse;
import com.ureca.gate.review.controller.inputport.ReviewService;
import com.ureca.gate.review.controller.request.ReviewSaveRequest;
import com.ureca.gate.review.domain.Review;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Review API", description = "리뷰 API")
@RequestMapping("/api/v1/reviews")
@RestController
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "리뷰 작성 API", description = "사진, 영상, 텍스트를 포함한 리뷰 작성")
  public SuccessResponse<Object> create(@AuthenticationPrincipal Long memberId, @RequestPart
      ReviewSaveRequest request, @RequestPart(required = false) List<MultipartFile> files)
      throws IOException {
    Review review = reviewService.create(memberId, request, files);
    return SuccessResponse.successWithoutResult(null);
  }
}
