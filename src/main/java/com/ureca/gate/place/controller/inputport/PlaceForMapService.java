package com.ureca.gate.place.controller.inputport;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.dto.response.PageResponse;
import com.ureca.gate.place.controller.response.PlaceForMapResponse;
import com.ureca.gate.place.controller.response.PlaceSearchForMapResponse;

import java.util.List;

public interface PlaceForMapService {

    List<PlaceForMapResponse> getPlacesForMap(Long memberId, Double latitude, Double longitude, String query, String category, Size size, List<String> entryConditions, List<String> types);

    PageResponse<PlaceSearchForMapResponse> getPlacesBySearch(Long memberId, Double latitude, Double longitude,
                                                              String query, String category, Size size,
                                                              List<String> entryConditions, List<String> types, int page);
}

