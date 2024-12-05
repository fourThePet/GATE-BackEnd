package com.ureca.gate.review.application;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.PLACE_NOT_FOUND;

import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.global.exception.errorcode.CommonErrorCode;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.review.application.outputport.KeywordRepository;
import com.ureca.gate.review.controller.inputport.KeywordService;
import com.ureca.gate.review.domain.Keyword;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {

  private final KeywordRepository keywordRepository;
  private final PlaceRepository placeRepository;

  @Transactional
  public List<Keyword> getKeywords(Long placeId) {
    Place place = placeRepository.findById(placeId).orElseThrow(()->new BusinessException(PLACE_NOT_FOUND));
    return keywordRepository.findAllByCategoryId(place.getCategory().getId());
  }

  @Override
  public Keyword getById(Long keywordId) {
    return keywordRepository.findById(keywordId).orElseThrow(() -> new BusinessException(CommonErrorCode.KEYWORD_NOT_FOUND));
  }
}
