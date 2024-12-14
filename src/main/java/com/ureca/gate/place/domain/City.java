package com.ureca.gate.place.domain;


import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Getter
@Builder
public class City {
    private Long id;
    private String name;
    private String photoUrl;
    private Point locationPoint;  // Point(경도, 위도)
}
