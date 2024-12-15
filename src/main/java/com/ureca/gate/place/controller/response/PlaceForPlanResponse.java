package com.ureca.gate.place.controller.response;

import com.ureca.gate.place.domain.SearchPlace;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceForPlanResponse {
    @Schema(description = "장소 아이디", example = "1")
    private Long placeId;
    @Schema(description = "장소 이름", example = "냥냥이카페")
    private String placeName;
    @Schema(description = "위도", example = "위도")
    private Double latitude;
    @Schema(description = "경도", example = "경도")
    private Double longitude;
    @Schema(description = "장소 도로명주소", example = "~~~~")
    private String roadAddress;
    @Schema(description = "사진 url", example = "afawcdc")
    private String photoUrl;
    @Schema(description = "리뷰 갯수", example = "5")
    private Integer reviewNum;
    @Schema(description = "리뷰 평균 평점", example = "4.3")
    private Double starAvg;

    public static PlaceForPlanResponse from(SearchPlace searchPlace){
        return PlaceForPlanResponse.builder()
                .placeId(searchPlace.getId())
                .placeName(searchPlace.getPlaceName())
                .latitude(searchPlace.getLatitude())
                .longitude(searchPlace.getLongitude())
                .roadAddress(searchPlace.getRoadAddress())
                .photoUrl(searchPlace.getPhotoUrl())
                .reviewNum(searchPlace.getReviews() != null ? searchPlace.getReviews().size() : 0)
                .starAvg(searchPlace.getStarAvg())
                .build();}
}
