package com.ureca.gate.review.infrasturcture.jpaadapter;

import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewEntity;
import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewKeywordEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewKeywordJpaRepository extends JpaRepository<ReviewKeywordEntity, Long> {
  List<ReviewKeywordEntity> findAllByReviewEntity(ReviewEntity reviewEntity);

  void deleteAllByReviewEntity(ReviewEntity reviewEntity);
}
