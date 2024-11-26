package com.ureca.gate.review.controller;

import com.ureca.gate.global.dto.response.SuccessResponse;
import com.ureca.gate.review.controller.inputport.ReviewService;
import com.ureca.gate.review.controller.request.ReviewSaveRequest;
import com.ureca.gate.review.controller.response.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Review API", description = "리뷰 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
@RestController
public class ReviewController {

  private final ReviewService reviewService;

  @Operation(summary = "리뷰 단건 조회 API", description = "특정 리뷰 단건 조회")
  @GetMapping("/{id}")
  public SuccessResponse<Object> review(@PathVariable("id") Long id){
    List<String> tags = new ArrayList<>();
    tags.add("커피가 맛있어요");
    tags.add("친절해요");

    return SuccessResponse.success(
        ReviewResponse.builder()
            .id(id)
            .starRating(4.0)
            .receipt(true)
            .nickName("뽀삐")
            .placeName("더왈츠 애견카페")
            .sizeName("소형")
            .tags(tags)
            .content("다른 어떤 애견카페보다 좋았어요!")
            .createDate(LocalDate.of(2024, 1, 25))
            .build()
    );
  }

  //이미지, 동영상 파일 추가 기능 필요
  @Operation(summary = "리뷰 작성 API", description = "장소에 대한 새로운 리뷰 작성")
  @PostMapping
  public SuccessResponse<Object> create(@AuthenticationPrincipal Long userId, @RequestBody ReviewSaveRequest reviewSaveRequest){
    //placeId 처리 방법 필요
    Long placeId = 1L;
    return SuccessResponse.success(reviewService.create(userId, placeId, reviewSaveRequest));
  }
}
