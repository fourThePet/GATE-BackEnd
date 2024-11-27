package com.ureca.gate.place.infrastructure.jpaadapter.entity;


import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.entity.BaseTimeEntity;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.domain.enumeration.YesNo;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.vo.Address;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

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

    @Enumerated
    private Address address;
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

//    public static PlaceEntity from(Place place){
//        PlaceEntity placeEntity = new PlaceEntity();
//        placeEntity.id = place.getId();
//    }

    public Place toModel(){
        return Place.builder()
                .id(id)
                .name(name)
                .category(categoryEntity.toModel())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .postalCode(address.getPostalCode()) //우편번호
                .roadAddress(address.getRoadAddress())  //도로명
                .lotAddress(address.getLotAddress())  //지번번호
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
