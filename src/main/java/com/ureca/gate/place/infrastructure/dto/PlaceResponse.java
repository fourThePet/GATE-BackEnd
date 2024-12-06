package com.ureca.gate.place.infrastructure.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Getter
@Builder
public class PlaceResponse {
    private Long id;
    private String name;
    private String category;
    private String profileUrl;
    private Point locationPoint;
    private String roadAddress; //도로명 주소
    private String postalCode; //우편주소
    private Double distance;
//    private float star;
//    private Integer reviewNum;

    @QueryProjection
    public PlaceResponse(Long id, String name, String category, String profileUrl, Point locationPoint,String roadAddress, String postalCode, Double distance) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.profileUrl = profileUrl;
        this.locationPoint = locationPoint;
        this.roadAddress = roadAddress;
        this.postalCode = postalCode;
        this.distance = distance;
//        this.star = star;
//        this.reviewNum = reviewNum;
    }

    public static PlaceResponse from(PlaceEntity placeEntity){
        return PlaceResponse.builder()
                .id(placeEntity.getId())
                .name(placeEntity.getName())
                .category(placeEntity.getCategoryEntity().getName())
                .profileUrl(placeEntity.getPhotoUrl())
                .locationPoint(placeEntity.getAddressEntity().getLocationPoint())
                .roadAddress(placeEntity.getAddressEntity().getRoadAddress())
                .postalCode(placeEntity.getAddressEntity().getPostalCode())
                .build();
    }
}
