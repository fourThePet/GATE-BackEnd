package com.ureca.gate.place.controller;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.dto.response.SuccessResponse;
import com.ureca.gate.place.controller.inputport.PlaceCategoryService;
import com.ureca.gate.place.controller.inputport.PlaceDetailService;
import com.ureca.gate.place.controller.inputport.PlaceListService;
import com.ureca.gate.place.controller.response.PlaceCategoryResponse;
import com.ureca.gate.place.controller.response.PlaceDetailResponse;
import com.ureca.gate.place.controller.response.PlaceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Place API", description = "장소 API")
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
@RestController
public class PlaceController {
    private final PlaceCategoryService placeCategoryService;
    private final PlaceDetailService placeDetailService;
    private final PlaceListService placeListService;

    @Operation(summary = "카테고리 리스트 조회 API", description = "장소 카테고리 리스트 조회 API")
    @GetMapping("/categories")
    public SuccessResponse<List<PlaceCategoryResponse>> getPlaceCategories() {
        List<PlaceCategoryResponse> response = placeCategoryService.getPlaceCategories();
        return SuccessResponse.success(response);
    }

    @Operation(summary = "시설 정보 조회 API", description = "해당 장소의 상세정보 조회 API")
    @GetMapping("/{placeId}")
    public SuccessResponse<PlaceDetailResponse> getPlaceDetail(@PathVariable("placeId")Long placeId) {
        PlaceDetailResponse response = placeDetailService.getPlaceDetail(placeId);
        return SuccessResponse.success(response);
    }
    @Operation(summary = "시설 리스트 조회 API", description = "시설(장소) 리스트 조회 API")
    @GetMapping("")
    public SuccessResponse<List<PlaceResponse>> searchCities(@RequestParam("latitude") Double latitude,
                                                             @RequestParam("longitude") Double longitude,
                                                             @Parameter(description = "카테고리. 가능한 값: [의료,미용,반려동물용품,식당,카페,숙소,문화시설,여행지]",
                                                                     example = "의료")
                                                             @RequestParam(value = "category", required = false) String category,
                                                             @Parameter(description = "사이즈. 가능한 값: [LARGE,MEDIUM,SMALL]",
                                                                     example = "LARGE")
                                                             @RequestParam(value = "size", required = false) Size size,
                                                             @Parameter(description = "입장 제한 조건. 가능한 값: [isLeashRequired(목줄), isMuzzleRequired(입마개), isCageRequired(케이지), isVaccinationComplete(접종여부)]",
                                                                     example = "isLeashRequired,isMuzzleRequired")
                                                             @RequestParam(value = "entryConditions", required = false) List<String> entryConditions,
                                                             @Parameter(description = "타입 조건. 가능한 값: [parkingAvailable(주차 가능여부), indoorAvailable(실내 가능여부), outdoorAvailable(실외 가능여부)]",
                                                                     example = "parkingAvailable,indoorAvailable")
                                                             @RequestParam(value = "types", required = false) List<String> types) {

        List<PlaceResponse> response = placeListService.getPlaceList(latitude, longitude, category, size, entryConditions, types);
        return SuccessResponse.success(response);
    }

}
