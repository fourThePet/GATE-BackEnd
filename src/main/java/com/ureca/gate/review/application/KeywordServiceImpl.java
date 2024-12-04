package com.ureca.gate.review.application;

import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.global.exception.errorcode.CommonErrorCode;
import com.ureca.gate.review.application.outputport.KeywordRepository;
import com.ureca.gate.review.controller.inputport.KeywordService;
import com.ureca.gate.review.domain.Keyword;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {

  private final KeywordRepository keywordRepository;

  @Override
  public List<Keyword> getKeywords(Long categoryId) {
    return keywordRepository.findAllByCategoryId(categoryId);
  }

  @Override
  public Keyword getById(Long keywordId) {
    return keywordRepository.findById(keywordId).orElseThrow(() -> new BusinessException(CommonErrorCode.KEYWORD_NOT_FOUND));
  }
}
