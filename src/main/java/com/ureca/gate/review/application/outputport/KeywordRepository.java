package com.ureca.gate.review.application.outputport;

import com.ureca.gate.review.domain.Keyword;
import java.util.List;
import java.util.Optional;

public interface KeywordRepository {
  List<Keyword> findAllByCategoryId(Long categoryId);

  Optional<Keyword> findById(Long tagId);
}
