package com.ureca.gate.place.controller;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.dto.response.SliceResponse;
import com.ureca.gate.global.dto.response.SuccessResponse;
import com.ureca.gate.global.util.city.CityMapper;
import com.ureca.gate.place.controller.inputport.*;
import com.ureca.gate.place.controller.response.*;
import com.ureca.gate.place.domain.PopularPlace;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Place API", description = "장소 API")
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
@RestController
public class PlaceController {
    private final PlaceCategoryService placeCategoryService;
    private final PlaceDetailService placeDetailService;
    private final PlaceForMapService placeForMapService;
    private final PlaceForPlanService placeForPlanService;
    private final CityListService cityListService;
    private final ViewsService viewsService;
    private final PersonalizeService personalizeService;

    @Operation(summary = "카테고리 리스트 조회 API", description = "장소 카테고리 리스트 조회 API")
    @GetMapping("/categories")
    public SuccessResponse<List<PlaceCategoryResponse>> getPlaceCategories() {
        List<PlaceCategoryResponse> response = placeCategoryService.getPlaceCategories();
        return SuccessResponse.success(response);
    }
    @Operation(summary = "시설 정보 조회 API", description = "해당 장소의 상세정보 조회 API, 조회수 증가")
    @GetMapping("/{placeId}")
    public SuccessResponse<PlaceDetailResponse> getPlaceDetail(@AuthenticationPrincipal Long memberId,
                                                               @PathVariable("placeId")Long placeId) {
        PlaceDetailResponse response = placeDetailService.getPlaceDetail(memberId,placeId);
        return SuccessResponse.success(response);
    }
    @Operation(summary = "시설 리스트 조회 API", description = "시설(장소) 리스트 조회 API")
    @GetMapping("")
    public SuccessResponse<List<PlaceForMapResponse>> getPlacesForMap(@AuthenticationPrincipal Long memberId,
                                                                      @RequestParam(value = "query", required = false) String query,
                                                                      @RequestParam("latitude") Double latitude,
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

        List<PlaceForMapResponse> response = placeForMapService.getPlacesForMap(memberId,latitude, longitude, query, category, size, entryConditions, types);
        return SuccessResponse.success(response);
    }

    /**
     * TODO
     * 1. Order 처리 (검색어와 score(유사도) 내림차순 -> 평점 내림차순) 형태인데 평점이 낮은데 검색어와 유사도가 높은 순으로 출력이됨.
     * 2. (리뷰작성시)Redis Stream 으로 리스너처리
     */
    @Operation(summary = "일정(장소선택) 시설 리스트 조회 API", description = "일정(장소선택) 시설 리스트 조회 API - hasNext =true이면 다음페이자가 있다는 의미 (+더보기)")
    @GetMapping("/plan-search")
    public SuccessResponse<SliceResponse<PlaceForPlanResponse> > getPlacesForPlan(@RequestParam(value = "query", required = false) String query,
                                                                                 @Parameter(description = "지역", example = "1")
                                                                                 @RequestParam("cityId") Long cityId,
                                                                                 @Parameter(description = "카테고리. 가능한 값: [식당,카페,숙소,문화시설,여행지]",
                                                                                         example = "식당")
                                                                                 @RequestParam(value = "category", required = false) String category,
                                                                                 @Parameter(description = "페이지 순서 0부터 시작 ")
                                                                                 @RequestParam(value = "page", defaultValue = "0") int page){

        // cityId를 cityName으로 변환
        String city = CityMapper.getCityName(cityId);
        SliceResponse<PlaceForPlanResponse>  response = placeForPlanService.getPlacesForPlan(query,city, category, page);
        return SuccessResponse.success(response);
    }

    @Operation(summary = "지역 리스트 조회 API", description = "지역 리스트 조회 API")
    @GetMapping("/cities")
    public SuccessResponse<List<CityResponse>> getCities() {
        List<CityResponse> response = cityListService.getCityList();
        return SuccessResponse.success(response);
    }

    @Operation(summary = "인기 장소 리스트 API", description = "조회수 높은 장소 리스트 조회, 로그인 불필요")
    @GetMapping("/popular")
    public SuccessResponse<List<PopularPlaceResponse>> getPopularPlaces(@Parameter(description = "인기 순위 제한 (10 입력시, 조회수 상위 1위부터 10위까지 출력)", example = "10")
                                                                        @RequestParam int limit) {
        List<PopularPlace> popularPlaces = viewsService.getPopularPlaces(limit);
        List<PopularPlaceResponse> response = PopularPlaceResponse.from(popularPlaces);
        return SuccessResponse.success(response);
    }

    @GetMapping("/personalize")
    public SuccessResponse<List<String>> getPersonalize(@RequestParam(value = "city", required = false) String city
                                                        ) {
        Long memberId = 2L; // 이 부분은 실제 로그인한 사용자의 id를 받아오는 걸로 변경 필요

        List<String> response = personalizeService.getRecommendations(memberId, city);
        return SuccessResponse.success(response);
    }
}
