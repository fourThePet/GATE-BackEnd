package com.ureca.gate.place.controller.inputport;

import com.ureca.gate.global.dto.response.SliceResponse;
import com.ureca.gate.place.controller.response.PlaceForPlanResponse;

public interface PlaceForPlanService {
    SliceResponse<PlaceForPlanResponse> getPlacesForPlan(String query, String city, String category, int page);

}
