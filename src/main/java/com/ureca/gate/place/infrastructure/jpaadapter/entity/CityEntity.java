package com.ureca.gate.place.infrastructure.jpaadapter.entity;


import com.ureca.gate.place.domain.City;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name ="city")
@Getter
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    private String name;

    private String photoUrl;

    @Column(nullable = false, columnDefinition = "geography(Point, 4326)")
    private Point locationPoint; // Point(경도, 위도)

    public static CityEntity from(City city){
        CityEntity cityEntity = new CityEntity();
        cityEntity.id = city.getId();
        cityEntity.name = city.getName();
        cityEntity.photoUrl = city.getPhotoUrl();
        cityEntity.locationPoint = city.getLocationPoint();
        return cityEntity;
    }

    public City toModel(){
        return City.builder()
                .id(id)
                .name(name)
                .photoUrl(photoUrl)
                .locationPoint(locationPoint)
                .build();
    }
}
