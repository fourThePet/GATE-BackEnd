package com.ureca.gate.review.infrasturcture.jpaadapter;

import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.global.exception.errorcode.CommonErrorCode;
import com.ureca.gate.review.application.outputport.ReviewFileRepository;
import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.domain.ReviewFile;
import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewEntity;
import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewFileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewFileRepositoryImpl implements ReviewFileRepository {

  private final ReviewFileJpaRepository reviewFileJpaRepository;
  private final ReviewJpaRepository reviewJpaRepository;

  @Override
  public ReviewFile save(ReviewFile reviewFile) {
    Review review = reviewJpaRepository.findById(reviewFile.getReviewId()).map(ReviewEntity::toModel).orElseThrow(()->new BusinessException(CommonErrorCode.REVIEW_NOT_FOUND));
    return reviewFileJpaRepository.save(ReviewFileEntity.from(reviewFile, review)).toModel();
  }

  @Override
  public void delete(ReviewFile reviewFile) {
    Review review = reviewJpaRepository.findById(reviewFile.getReviewId()).map(ReviewEntity::toModel).orElseThrow(()->new BusinessException(CommonErrorCode.REVIEW_NOT_FOUND));
    reviewFileJpaRepository.delete(ReviewFileEntity.from(reviewFile, review));
  }
}
