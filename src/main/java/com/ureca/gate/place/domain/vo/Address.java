package com.ureca.gate.place.domain.vo;

import com.ureca.gate.place.domain.City;

import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Getter
@Builder
public class Address {

    private City city;
    private String district;
    private String town;
    private String village;
    private String lotNumber;
    private String road;
    private String buildingNumber;
    private Point locationPoint; // Point(경도, 위도)
    private String postalCode;
    private String roadAddress;
    private String lotAddress;
}
