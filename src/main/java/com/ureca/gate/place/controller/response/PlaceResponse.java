package com.ureca.gate.place.controller.response;

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
    private double latitude;
    private double longitude;
    @Schema(description = "도로명주소", example = "경기도 고양시 덕양구 동세로 19")
    private String roadAddress; //도로명 주소
    @Schema(description = "우편주소", example = "12465")
    private String postalCode; //우편주소
//    @Schema(description = "평균별", example = "4.5")
//    private float star;
//    @Schema(description = "리뷰수", example = "1")
//    private Integer reviewNum;


    public static PlaceResponse from(com.ureca.gate.place.infrastructure.dto.PlaceResponse place){
        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .category(place.getCategory())
                .latitude(place.getLocationPoint().getY())
                .longitude(place.getLocationPoint().getX())
                .roadAddress(place.getRoadAddress())
                .postalCode(place.getPostalCode())
                .build();
    }
}
