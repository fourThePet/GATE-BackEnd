package com.ureca.gate.place.infrastructure.jpaadapter.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String city;
    private String district;
    private String town;
    private String village;
    private String lotNumber;
    private String road;
    private String buildingNumber;
    @Column(nullable = false, columnDefinition = "POINT SRID 4326")
    private Point locationPoint; // Point(경도, 위도)
    private String postalCode;
    private String roadAddress;
    private String lotAddress;

    @Builder
    public Address(String city, String district, String town, String village, String lotNumber, String road, String buildingNumber, Point locationPoint, String postalCode, String roadAddress, String lotAddress) {
        this.city = city;
        this.district = district;
        this.town = town;
        this.village = village;
        this.lotNumber = lotNumber;
        this.road = road;
        this.buildingNumber = buildingNumber;
        this.locationPoint = locationPoint;
        this.postalCode = postalCode;
        this.roadAddress = roadAddress;
        this.lotAddress = lotAddress;
    }
}
