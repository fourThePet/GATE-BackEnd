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

    // 지역 정보를 기반으로 Address 객체 생성
    public static Address fromRegionString(String regionString) {
        String[] regionParts = regionString.split("\\s+");
        City city = City.builder()
                .name(regionParts[0])
                .build();
        String district = regionParts.length > 1 ? regionParts[1] : null;
        String town = regionParts.length == 3 ? regionParts[2] : null;

        return Address.builder()
                .city(city)  // City 객체로 지역 설정
                .district(district)
                .town(town)
                .build();
    }
}
