package com.ureca.gate.place.controller.inputport;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.place.controller.response.PlaceResponse;

import java.util.List;

public interface PlaceListService {

    public List<PlaceResponse> getPlaceList(Double latitude, Double longitude, String category, Size size, List<String> entryConditions, List<String> types);
}
