package com.ureca.gate.place.controller.inputport;

import com.ureca.gate.global.dto.response.SliceResponse;
import com.ureca.gate.place.controller.response.PlaceForPlanResponse;

import java.util.List;

public interface PersonalizeService {
    List<String> getRecommendations(Long memberId, String city);
    SliceResponse<PlaceForPlanResponse> getRecommendations(Long memberId, Long cityId, int page);
}
