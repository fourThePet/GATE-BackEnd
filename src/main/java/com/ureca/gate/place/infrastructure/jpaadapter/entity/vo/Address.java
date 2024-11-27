package com.ureca.gate.place.infrastructure.jpaadapter.entity.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Double latitude;
    private Double longitude;
    private String postalCode;
    private String roadAddress;
    private String lotAddress;

    @Builder
    public Address(String city, String district, String town, String village, String lotNumber, String road, String buildingNumber, Double latitude, Double longitude, String postalCode, String roadAddress, String lotAddress) {
        this.city = city;
        this.district = district;
        this.town = town;
        this.village = village;
        this.lotNumber = lotNumber;
        this.road = road;
        this.buildingNumber = buildingNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.postalCode = postalCode;
        this.roadAddress = roadAddress;
        this.lotAddress = lotAddress;
    }
}
