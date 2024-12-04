package com.ureca.gate.review.infrasturcture.jpaadapter;

import com.ureca.gate.review.application.outputport.KeywordRepository;
import com.ureca.gate.review.domain.Keyword;
import com.ureca.gate.review.infrasturcture.jpaadapter.entity.KeywordEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class KeywordRepositoryImpl implements KeywordRepository {

  private final KeywordJpaRepository keywordJpaRepository;

  @Override
  public List<Keyword> findAllByCategoryId(Long categoryId) {
    return keywordJpaRepository.findAllByCategoryId(categoryId).stream().map(KeywordEntity::toModel).toList();
  }

  @Override
  public Optional<Keyword> findById(Long keywordId) {
    return keywordJpaRepository.findById(keywordId).map(KeywordEntity::toModel);
  }

}
