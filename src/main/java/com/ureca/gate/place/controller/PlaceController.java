package com.ureca.gate.place.controller;

import com.ureca.gate.global.dto.response.SuccessResponse;
import com.ureca.gate.place.controller.inputport.PlaceCategoryService;
import com.ureca.gate.place.controller.response.PlaceCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Place API", description = "장소 API")
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
@RestController
public class PlaceController {
    private final PlaceCategoryService placeCategoryService;

    @Operation(summary = "카테고리 리스트 조회 API", description = "장소 카테고리 리스트 조회 API")
    @GetMapping("/categories")
    public SuccessResponse<List<PlaceCategoryResponse>> getPlaceCategories() {
        List<PlaceCategoryResponse> response = placeCategoryService.getPlaceCategories();
        return SuccessResponse.success(response);
    }

}
