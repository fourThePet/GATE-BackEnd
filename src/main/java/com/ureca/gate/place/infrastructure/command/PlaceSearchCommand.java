package com.ureca.gate.place.infrastructure.command;

import com.ureca.gate.place.infrastructure.elasticsearchadapter.Document.PlaceElastic;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class PlaceSearchCommand {
    private Long id;
    private String name;
    private String category;
    private String profileUrl;
    private Double latitude;
    private Double longitude;
    private String roadAddress; //도로명 주소
    private Double distance;


    public static PlaceSearchCommand from(Long id,PlaceElastic placeElastic,Double distance){
        return PlaceSearchCommand.builder()
                .id(id)
                .name(placeElastic.getName())
                .category(placeElastic.getCategory())
                .profileUrl(placeElastic.getProfileUrl())
                .latitude(placeElastic.getLocation().getLat())
                .longitude(placeElastic.getLocation().getLon())
                .roadAddress(placeElastic.getRoadAddress())
                .distance(Math.round((distance/ 1000.0) * 1000) / 1000.0)
                .build();
    }
}
