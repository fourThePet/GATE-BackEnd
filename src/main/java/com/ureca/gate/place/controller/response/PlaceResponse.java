package com.ureca.gate.place.controller.response;

import com.ureca.gate.place.domain.enumeration.YesNo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceResponse {
    @Schema(description = "장소 아이디", example = "1")
    private Long id;
    @Schema(description = "장소 이름", example = "멍멍이카페")
    private String name;
    @Schema(description = "장소 카테고리", example = "카페")
    private String category;
    @Schema(description = "장소 이미지", example = "url")
    private String profileUrl;
    @Schema(description = "위도", example = "위도")
    private double latitude;
    @Schema(description = "경도", example = "경도")
    private double longitude;
    @Schema(description = "도로명주소", example = "경기도 고양시 덕양구 동세로 19")
    private String roadAddress; //도로명 주소
    @Schema(description = "우편주소", example = "12465")
    private String postalCode; //우편주소
    @Schema(description = "거리(Km)", example = "0.24")
    private Double distance;
    @Schema(description = "즐겨찾기 여부[Y/N]", example = "N")
    private YesNo favorites;

//    @Schema(description = "평균별", example = "4.5")
//    private float star;
//    @Schema(description = "리뷰수", example = "1")
//    private Integer reviewNum;


    public static PlaceResponse from(com.ureca.gate.place.infrastructure.dto.PlaceResponse place,YesNo favorites){
        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .category(place.getCategory())
                .profileUrl(place.getProfileUrl())
                .latitude(place.getLocationPoint().getY())
                .longitude(place.getLocationPoint().getX())
                .roadAddress(place.getRoadAddress())
                .postalCode(place.getPostalCode())
                .distance(Math.round((place.getDistance() / 1000.0) * 1000) / 1000.0)
                .favorites(favorites)
                .build();
    }
}
