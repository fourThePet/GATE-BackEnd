package com.ureca.gate.place.infrastructure.jpaadapter.entity.vo;

import com.ureca.gate.place.domain.vo.Address;
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
public class AddressEntity {

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
    public AddressEntity(CityEntity cityEntity, String district, String town, String village, String lotNumber, String road, String buildingNumber, Point locationPoint, String postalCode, String roadAddress, String lotAddress) {
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
    public static AddressEntity from(Address address) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.cityEntity = CityEntity.from(address.getCity()); // City 변환 필요
        addressEntity.district = address.getDistrict();
        addressEntity.town = address.getTown();
        addressEntity.village = address.getVillage();
        addressEntity.lotNumber = address.getLotNumber();
        addressEntity.road = address.getRoad();
        addressEntity.buildingNumber = address.getBuildingNumber();
        addressEntity.locationPoint = address.getLocationPoint();
        addressEntity.postalCode = address.getPostalCode();
        addressEntity.roadAddress = address.getRoadAddress();
        addressEntity.lotAddress = address.getLotAddress();
        return addressEntity;
    }

    public Address toModel() {
        return Address.builder()
                .city(cityEntity.toModel()) // City 변환 필요
                .district(district)
                .town(town)
                .village(village)
                .lotNumber(lotNumber)
                .road(road)
                .buildingNumber(buildingNumber)
                .locationPoint(locationPoint)
                .postalCode(postalCode)
                .roadAddress(roadAddress)
                .lotAddress(lotAddress)
                .build();
    }


}