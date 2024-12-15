package com.ureca.gate.place.application;

import com.ureca.gate.global.domain.CustomSlice;
import com.ureca.gate.global.dto.response.SliceResponse;
import com.ureca.gate.place.application.outputport.PlaceElasticRepository;
import com.ureca.gate.place.controller.inputport.PlaceForPlanService;
import com.ureca.gate.place.controller.response.PlaceForPlanResponse;
import com.ureca.gate.place.domain.SearchPlace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PlaceForPlanServiceImpl implements PlaceForPlanService {
    private final PlaceElasticRepository placeElasticRepository;
    @Override
    public SliceResponse<PlaceForPlanResponse>  getPlacesForPlan(String query, String city, String category, int page) {

        log.info("Executing search for query: {}, city: {}, category: {}", query, city, category);

        Pageable pageable = PageRequest.of(page, 20,
                Sort.by(Sort.Order.desc("_score"), Sort.Order.desc("starAvg"))); // _score(정확도)와 starAvg(평점) 기준 정렬

        CustomSlice<SearchPlace> customSlice =placeElasticRepository.findByQueryAndCategoryAndCity(query, city, category,pageable);

        return SliceResponse.from(customSlice, PlaceForPlanResponse::from);

    }
}
