package com.ureca.gate.place.controller.inputport;

import com.ureca.gate.place.controller.response.PlaceDetailResponse;

public interface PlaceDetailService {
   PlaceDetailResponse getPlaceDetail(Long memberId, Long placeId);
}
