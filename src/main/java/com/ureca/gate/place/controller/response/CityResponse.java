package com.ureca.gate.place.controller.response;

import com.ureca.gate.place.domain.City;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CityResponse {
    @Schema(description = "지역 아이디", example = "1")
    private Long id;
    @Schema(description = "지역 이름", example = "서울")
    private String cityName;

    public static CityResponse from(City city){
        return CityResponse.builder()
                .id(city.getId())
                .cityName(city.getName())
                .build();
    }

}
