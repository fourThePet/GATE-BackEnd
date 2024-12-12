package com.ureca.gate.place.infrastructure.jpaadapter.entity;


import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.entity.BaseTimeEntity;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.domain.enumeration.YesNo;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.vo.AddressEntity;
import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="place")
@Getter
public class PlaceEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Column(name = "facility_name")
    private String name;

    @JoinColumn(name ="category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity categoryEntity;

    @Embedded
    private AddressEntity addressEntity;
    private String phoneNumber;
    private String photoUrl;
    private String website;
    private String holiday; //휴무일
    private String operatingHours; //운영시간
    private String addPetFee; //애견동반시 추가비용
    private String admissionFee; //입장료 추가요금
    private String restriction; //제한사항
    private String size; //멍멍이 사이즈
    private String isLeashRequired; // 목줄 필수 여부
    private String isMuzzleRequired; // 입마개 필수 여부
    private String isCageRequired; // 케이지 필수 여부
    private String isVaccinationComplete; // 접종 완료 여부
    private String parkingAvailable; //주차여부
    private String indoorAvailable; //실내이용여부
    private String outdoorAvailable; //실외이용여부
    private LocalDate lastUpdated; //데이터 마지막 수정일

    @OneToMany(mappedBy = "placeEntity", cascade = CascadeType.ALL)
    List<ReviewEntity> reviewEntityList = new ArrayList<>();

    public static PlaceEntity from(Place place){
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.categoryEntity = CategoryEntity.from(place.getCategory());
        placeEntity.id = place.getId();
        placeEntity.name = place.getName();
        placeEntity.categoryEntity = CategoryEntity.from(place.getCategory());
        placeEntity.addressEntity = AddressEntity.from(place.getAddress());
        placeEntity.phoneNumber =place.getPhoneNumber();
        placeEntity.photoUrl= place.getPhotoUrl();
        placeEntity.website= place.getWebsiteUrl();
        placeEntity.holiday= place.getHoliday();
        placeEntity.operatingHours= place.getOperatingHours();
        placeEntity.addPetFee= place.getAddPetFee();
        placeEntity.admissionFee= place.getAdmissionFee();
        placeEntity.restriction= place.getRestriction();
        placeEntity.size= place.getSize().name();
        placeEntity.isLeashRequired = place.getIsLeashRequired().name();
        placeEntity.isMuzzleRequired= place.getIsMuzzleRequired().name();
        placeEntity.isCageRequired= place.getIsCageRequired().name();
        placeEntity.isVaccinationComplete= place.getIsVaccinationComplete().name();
        placeEntity.parkingAvailable= place.getParkingAvailable().name();
        placeEntity.indoorAvailable= place.getIndoorAvailable().name();
        placeEntity.outdoorAvailable= place.getOutdoorAvailable().name();
        placeEntity.lastUpdated= place.getLastUpdated();
        return placeEntity;
    }

    public Place toModel(){
        return Place.builder()
                .id(id)
                .name(name)
                .category(categoryEntity.toModel())
                .address(addressEntity.toModel())
                .phoneNumber(phoneNumber)
                .photoUrl(photoUrl)
                .websiteUrl(website)
                .holiday(holiday)
                .operatingHours(operatingHours)
                .addPetFee(addPetFee)  //반려견 동반시 추가요금
                .admissionFee(admissionFee)  //입장(입장료) 요금
                .restriction(restriction)  //제한사항
                .size(Size.from(size))  //멍멍이 사이즈
                .isLeashRequired(YesNo.from(isLeashRequired))  // 목줄 필수 여부
                .isMuzzleRequired(YesNo.from(isMuzzleRequired))  // 입마개 필수 여부
                .isCageRequired(YesNo.from(isCageRequired)) // 케이지 필수 여부
                .isVaccinationComplete(YesNo.from(isVaccinationComplete)) // 접종 완료 여부
                .parkingAvailable(YesNo.from(parkingAvailable))  //주차여부
                .indoorAvailable(YesNo.from(indoorAvailable))  //실내이용여부
                .outdoorAvailable(YesNo.from(outdoorAvailable))  //실외이용여부
                .lastUpdated(lastUpdated)  //데이터 마지막 수정일
                .createAt(getCreateAt())
                .updateAt(getUpdateAt())
                .build();
    }



}
