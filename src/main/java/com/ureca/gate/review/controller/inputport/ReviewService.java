package com.ureca.gate.review.controller.inputport;

import com.ureca.gate.member.domain.Member;
import com.ureca.gate.review.controller.request.ReviewSaveRequest;
import com.ureca.gate.review.controller.response.PlaceReviewResponse;
import com.ureca.gate.review.controller.response.ReviewResponse;
import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.domain.ReviewFile;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {
  Review getById(Long reviewId);

  List<ReviewResponse> getAllByMember(Long memberId);

  PlaceReviewResponse getAllByPlace(Long placeId);

  ReviewResponse create(Long memberId, ReviewSaveRequest request, List<MultipartFile> files)
      throws IOException;

  Review update(Long memberId, Long reviewId, ReviewSaveRequest request, List<MultipartFile> files)
      throws IOException;

  void delete(Long reviewId);

  List<String> getFileUrlList(Long memberId, List<ReviewFile> files);
}
