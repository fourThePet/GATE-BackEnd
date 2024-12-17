package com.ureca.gate.place.controller.response;

import com.ureca.gate.place.domain.enumeration.YesNo;
import com.ureca.gate.place.infrastructure.command.PlaceSearchCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class PlaceSearchForMapResponse {
    private Long id;
    private String name;
    private String category;
    private String profileUrl;
    private Double latitude;
    private Double longitude;
    private String roadAddress; //도로명 주소
    private Double distance;
    @Schema(description = "즐겨찾기 여부[Y/N]", example = "N")
    private YesNo favorites;


    public static PlaceSearchForMapResponse from(PlaceSearchCommand placeSearchCommand, YesNo favorites){
        return PlaceSearchForMapResponse.builder()
                .id(placeSearchCommand.getId())
                .name(placeSearchCommand.getName())
                .category(placeSearchCommand.getCategory())
                .profileUrl(placeSearchCommand.getProfileUrl())
                .latitude(placeSearchCommand.getLatitude())
                .longitude(placeSearchCommand.getLongitude())
                .roadAddress(placeSearchCommand.getRoadAddress())
                .favorites(favorites)
                .build();
    }

}
