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
    @Schema(description = "사진 url", example = "https://encrypted-tbn2.gstatic.com/licensed-image?q=tbn:ANd9GcSqY5qzl_afPdIYFkXhZ8zU5nBSviNozTg_D4KyDdRyzO6ae345vCEiMnSEh9rM3DTsRErcpxwDNRxXbuw9B8Zeh3K9ZAPBGTNPzh2UpA")
    private String photoUrl;

    public static CityResponse from(City city){
        return CityResponse.builder()
                .id(city.getId())
                .cityName(city.getName())
                .photoUrl(city.getPhotoUrl())
                .build();
    }

}
