package com.ureca.gate.place.controller.response;

import com.ureca.gate.place.domain.PopularPlace;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PopularPlaceResponse {

    @Schema(description = "장소 아이디", example = "1")
    private final Long placeId;

    @Schema(description = "장소 이름", example = "멍멍이카페")
    private final String placeName;

    @Schema(description = "카테고리 이름", example = "카페")
    private final String categoryName;

    @Schema(description = "도시 이름", example = "경기도")
    private final String cityName;

    @Schema(description = "장소 이미지", example = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=AdDdOWpBtzRCmDmbKwQ2BgpfaP1Dr7R85ziocgeJdTisv_VKjsVYeMA3Tg0Xi5k8EuPUyhZU7Z4jIEP0Xr9tH0dfeAmo-aOuvL2bXTk3G1xNqDPuDvDoKjzwzlfsFvvnYqWANkP7lfiQfCFaKx3b7cE2ryrdQdVTapXjz23ZWwJ9jGn1FJ")
    private final String photoUrl;

    @Builder
    public PopularPlaceResponse(Long placeId, String placeName, String categoryName, String cityName, String photoUrl) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.categoryName = categoryName;
        this.cityName = cityName;
        this.photoUrl = photoUrl;
    }

    public static List<PopularPlaceResponse> from(List<PopularPlace> popularPlaces) {
        return popularPlaces.stream()
                .map(popularPlace -> PopularPlaceResponse.builder()
                        .placeId(popularPlace.getPlaceId())
                        .placeName(popularPlace.getPlaceName())
                        .categoryName(popularPlace.getCategoryName())
                        .cityName(popularPlace.getCityName())
                        .photoUrl(popularPlace.getPhotoUrl())
                        .build())
                .toList();
    }
}
