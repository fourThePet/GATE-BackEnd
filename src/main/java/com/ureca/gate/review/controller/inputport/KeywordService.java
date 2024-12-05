package com.ureca.gate.review.controller.inputport;

import com.ureca.gate.review.domain.Keyword;
import java.util.List;

public interface KeywordService {
  List<Keyword> getKeywords(Long placeId);

  Keyword getById(Long tagId);
}
