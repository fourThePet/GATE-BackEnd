package com.ureca.gate.review.infrasturcture.jpaadapter;

import com.ureca.gate.review.application.outputport.ReviewFileRepository;
import com.ureca.gate.review.domain.ReviewFile;
import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewFileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewFileRepositoryImpl implements ReviewFileRepository {

  private final ReviewFileJpaRepository reviewFileJpaRepository;

  @Override
  public ReviewFile save(ReviewFile reviewFile) {
    return reviewFileJpaRepository.save(ReviewFileEntity.from(reviewFile)).toModel();
  }
}
