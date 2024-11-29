package com.ureca.gate.place.infrastructure.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceResponse {
    private Long id;
    private String name;
    private String category;
    private String roadAddress; //도로명 주소
    private String postalCode; //우편주소
//    private float star;
//    private Integer reviewNum;

    @QueryProjection
    public PlaceResponse(Long id, String name, String category, String roadAddress, String postalCode) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.roadAddress = roadAddress;
        this.postalCode = postalCode;
//        this.star = star;
//        this.reviewNum = reviewNum;
    }

    public static PlaceResponse from(PlaceEntity placeEntity){
        return PlaceResponse.builder()
                .id(placeEntity.getId())
                .name(placeEntity.getName())
                .category(placeEntity.getCategoryEntity().getName())
                .roadAddress(placeEntity.getAddress().getRoadAddress())
                .postalCode(placeEntity.getAddress().getPostalCode())
                .build();
    }
}
