package com.ureca.gate.favorites.controller.response;

import com.ureca.gate.favorites.infrastructure.command.PlaceReviewInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesResponse{
    @Schema(description = "장소 아이디", example = "1")
    private Long placeId;
    @Schema(description = "장소 이름", example = "냥냥이카페")
    private String placeName;
    @Schema(description = "위도", example = "위도")
    private double latitude;
    @Schema(description = "경도", example = "경도")
    private double longitude;
    @Schema(description = "장소 도로명주소", example = "~~~~")
    private String roadAddress;
    @Schema(description = "사진 url", example = "afawcdc")
    private String photoUrl;
    @Schema(description = "리뷰 갯수", example = "5")
    private Integer reviewNum;
    @Schema(description = "리뷰 평균 평점", example = "4.3")
    private Double starAvg;

    public static FavoritesResponse from(PlaceReviewInfo placeReviewInfo){
        return FavoritesResponse.builder()
                .placeId(placeReviewInfo.getPlaceId())
                .placeName(placeReviewInfo.getPlaceName())
                .latitude(placeReviewInfo.getPoint().getY())
                .longitude(placeReviewInfo.getPoint().getX())
                .roadAddress(placeReviewInfo.getRoadAddress())
                .photoUrl(placeReviewInfo.getPhotoUrl())
                .reviewNum(placeReviewInfo.getReviewNum())
                .starAvg(placeReviewInfo.getStarAvg())
                .build();}
}
