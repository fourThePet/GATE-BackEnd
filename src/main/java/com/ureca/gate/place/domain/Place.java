package com.ureca.gate.place.domain;


import com.ureca.gate.place.domain.enumeration.PetSize;
import com.ureca.gate.place.domain.enumeration.YesNo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Place {
    private Long id;
    private String name;
    private Category category;
    private float latitude;
    private float longitude;
    private Integer postalCode;
    private String roadAddress;
    private String lotAddress;
    private String phoneNumber;
    private String webSiteUrl;
    private String holiday;
    private String operatingHours;
    private YesNo parkingAvailable;
    private String admissionFee;
    private YesNo isLeashRequired; //목출 필수 여부
    private YesNo isMuzzleRequired; //입마개 필수 여부
    private YesNo isCageRequired; //케이지 필수 여부
    private YesNo isVaccinated; //접종여부
    private YesNo indoorAvailable;
    private YesNo outdoorAvailable;
    private String additionalPetFee;
    private LocalDate lastUpdated;
    private PetSize size;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
