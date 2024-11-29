package com.ureca.gate.place.infrastructure.jpaadapter.entity.vo;

import com.ureca.gate.place.infrastructure.jpaadapter.entity.CityEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @JoinColumn(name = "city_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CityEntity cityEntity;
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
    public Address(CityEntity cityEntity, String district, String town, String village, String lotNumber, String road, String buildingNumber, Point locationPoint, String postalCode, String roadAddress, String lotAddress) {
        this.cityEntity = cityEntity;
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
