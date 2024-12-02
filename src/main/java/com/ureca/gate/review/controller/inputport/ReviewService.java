package com.ureca.gate.review.controller.inputport;

import com.ureca.gate.review.controller.request.ReviewSaveRequest;
import com.ureca.gate.review.domain.Review;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {
  Review create(Long memberId, ReviewSaveRequest request, List<MultipartFile> files)
      throws IOException;
}
