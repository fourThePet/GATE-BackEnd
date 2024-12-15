package com.ureca.gate.place.controller.inputport;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.place.controller.response.PlaceForMapResponse;

import java.util.List;

public interface PlaceForMapService {

    List<PlaceForMapResponse> getPlacesForMap(Long memberId, Double latitude, Double longitude, String query, String category, Size size, List<String> entryConditions, List<String> types);
}
