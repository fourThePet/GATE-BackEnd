package com.ureca.gate.place.infrastructure.command;

import lombok.*;
import org.locationtech.jts.geom.Point;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceForPlanCommand {
    private Long placeId;
    private String placeName;
    private Point locationPoint;
    private String roadAddress;
    private String photoUrl;
    private Long reviewNum;
    private Double starAvg;
}
