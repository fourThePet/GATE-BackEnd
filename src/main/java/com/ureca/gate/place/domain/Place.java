package com.ureca.gate.place.domain;


import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.place.domain.enumeration.YesNo;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class Place {
    private Long id;
    private String name;
    private Category category;
    private Point locationPoint;
    private String postalCode; //우편번호
    private String roadAddress; //도로명
    private String lotAddress; //지번번호
    private String phoneNumber;
    private String photoUrl;
    private String websiteUrl;
    private String holiday;
    private String operatingHours;
    private String addPetFee; //반려견 동반시 추가요금
    private String admissionFee; //입장(입장료) 요금
    private String restriction; //제한사항
    private Size size; //멍멍이 사이즈
    private YesNo isLeashRequired; // 목줄 필수 여부
    private YesNo isMuzzleRequired; // 입마개 필수 여부
    private YesNo isCageRequired; // 케이지 필수 여부
    private YesNo isVaccinationComplete; // 접종 완료 여부
    private YesNo parkingAvailable; //주차여부
    private YesNo indoorAvailable; //실내이용여부
    private YesNo outdoorAvailable; //실외이용여부
    private LocalDate lastUpdated; //데이터 마지막 수정일
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
