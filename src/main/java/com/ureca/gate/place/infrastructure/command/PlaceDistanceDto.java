package com.ureca.gate.place.infrastructure.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceDistanceDto {
    private Long placeId;
    private Double distance;

    public static PlaceDistanceDto from(Long placeId,Double distance){
       return PlaceDistanceDto.builder()
                .placeId(placeId)
                .distance(Math.round((distance/ 1000.0) * 1000) / 1000.0)
                .build();
    }
}
