package com.ureca.gate.favorites.infrastructure.command;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Getter
@Builder
public class PlaceReviewInfo {
    private Long placeId;
    private String placeName;
    private Point point;
    private String roadAddress;
    private String photoUrl;
    private Integer reviewNum;
    private Double starAvg;

    @QueryProjection
    public PlaceReviewInfo(Long placeId, String placeName, Point point, String roadAddress, String photoUrl, Integer reviewNum, Double starAvg) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.point = point;
        this.roadAddress = roadAddress;
        this.photoUrl = photoUrl;
        this.reviewNum = reviewNum;
        this.starAvg = starAvg;
    }
}
