package com.ureca.gate.place.controller.response;


import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.dog.domain.enumeration.DogSizeGroup;
import com.ureca.gate.place.domain.enumeration.YesNo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class PlaceDetailResponse {
    @Schema(description = "장소 아이디", example = "1")
    private Long id;
    @Schema(description = "장소 이름", example = "1004 약국")
    private String name;
    @Schema(description = "장소 카테고리", example = "약국")
    private String category;
    @Schema(description = "우편주소", example = "12465")
    private String postalCode; //우편번호
    @Schema(description = "도로명주소", example = "경기도 고양시 덕양구 동세로 19")
    private String roadAddress; //도로명 주소
    @Schema(description = "지번주소", example = "경기도 고양시 덕양구 동산동 352-1")
    private String lotAddress; //지번 주소
    @Schema(description = "전화번호", example = "02-246-1245")
    private String phoneNumber;
    @Schema(description = "홈페이지 url", example = "홈페이지 url")
    private String websiteUrl;
    @Schema(description = "장소 이미지", example = "url")
    private String photoUrl;
    @Schema(description = "휴무일", example = "매주 월요일")
    private String holiday; //휴무일
    @Schema(description = "운영시간", example = "월~금 10:00~18:00, 토 10:00~14:00")
    private String operatingHours;
    @Schema(description = "애견동반시 추가 요금", example = "없음")
    private String additionalPetFee; //동반시 추가 요금
    @Schema(description = "입장료", example = "10000원")
    private String admissionFee; //입장(입장료) 요금
    @Schema(description = "제한사항", example = "야외테라스만 동반 가능")
    private String restriction; //제한사항
    @Schema(description = "입장가능 크기[SMALL/MEDIUM/LARGE]", example = "LARGE")
    private Size sizeAvailable; //입장가능 크기
    @Schema(description = "입장허용 크기 리스트[SMALL/MEDIUM/LARGE]", example = "[\"SMALL\", \"MEDIUM\", \"LARGE\"")
    private List<String> allowSizes; //허용크기 리스트
    @Schema(description = "입장허용 상세", example = "8kg 미만")
    private String allowedSize; //허용크기 리스트
    @Schema(description = "목줄 필수여부[Y/N]", example = "Y")
    private YesNo isLeashRequired; // 목줄 필수 여부
    @Schema(description = "입마개 필수여부[Y/N]", example = "Y")
    private YesNo isMuzzleRequired; // 입마개 필수 여부
    @Schema(description = "케이지 필수여부[Y/N]", example = "Y")
    private YesNo isCageRequired; // 케이지 필수 여부
    @Schema(description = "접종 완료여부[Y/N]", example = "Y")
    private YesNo isVaccinationComplete; // 접종 완료 여부
    @Schema(description = "주차 가능여부[Y/N]", example = "Y")
    private YesNo parkingAvailable; //주차여부
    @Schema(description = "실내 이용여부[Y/N]", example = "Y")
    private YesNo indoorAvailable; //실내이용여부
    @Schema(description = "실외 이용여부[Y/N]", example = "Y")
    private YesNo outdoorAvailable; //실외이용여부
    @Schema(description = "즐겨찾기 여부[Y/N]", example = "N")
    private YesNo favorites;
    @Schema(description = "데이터 마지막 수정일", example = "2022-02-10")
    private LocalDate lastUpdated; //데이터 마지막 수정일
    @Schema(description = "위도", example = "37.7519573928855")
    private double latitude;
    @Schema(description = "경도", example = "127.049286104824")
    private double longitude;

    public static PlaceDetailResponse from(Place place,YesNo isFavorite){
        return PlaceDetailResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .category(place.getCategory().getName())
                .postalCode(place.getAddress().getPostalCode())
                .roadAddress(place.getAddress().getRoadAddress())
                .lotAddress(place.getAddress().getLotAddress())
                .phoneNumber(place.getPhoneNumber())
                .websiteUrl(place.getWebsiteUrl())
                .photoUrl(place.getPhotoUrl())
                .holiday(place.getHoliday())
                .operatingHours(place.getOperatingHours())
                .additionalPetFee(place.getAddPetFee())
                .admissionFee(place.getAdmissionFee())
                .restriction(place.getRestriction())
                .sizeAvailable(place.getSize())
                .allowedSize(place.getAllowedSize())
                .allowSizes(DogSizeGroup.allowSizesBySize(place.getSize()))
                .isLeashRequired(place.getIsLeashRequired())
                .isMuzzleRequired(place.getIsMuzzleRequired())
                .isCageRequired(place.getIsCageRequired())
                .isVaccinationComplete(place.getIsVaccinationComplete())
                .indoorAvailable(place.getIndoorAvailable())
                .outdoorAvailable(place.getOutdoorAvailable())
                .parkingAvailable(place.getParkingAvailable())
                .favorites(isFavorite)
                .lastUpdated(place.getLastUpdated())
                .lastUpdated(place.getLastUpdated())
                .latitude(place.getAddress().getLocationPoint().getY())
                .longitude(place.getAddress().getLocationPoint().getX())
                .build();
    }

}
