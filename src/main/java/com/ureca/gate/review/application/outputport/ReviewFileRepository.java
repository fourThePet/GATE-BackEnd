package com.ureca.gate.review.application.outputport;

import com.ureca.gate.review.domain.ReviewFile;

public interface ReviewFileRepository {
  ReviewFile save(ReviewFile reviewFile);
}
