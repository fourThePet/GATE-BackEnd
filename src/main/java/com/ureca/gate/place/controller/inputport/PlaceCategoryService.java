package com.ureca.gate.place.controller.inputport;

import com.ureca.gate.place.controller.response.PlaceCategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PlaceCategoryService {
    List<PlaceCategoryResponse> getPlaceCategories();

}
