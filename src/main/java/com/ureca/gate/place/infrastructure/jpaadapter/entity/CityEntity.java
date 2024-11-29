package com.ureca.gate.place.infrastructure.jpaadapter.entity;


import com.ureca.gate.place.domain.City;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name ="city")
@Getter
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    private String name;

    public CityEntity from(City city){
        CityEntity cityEntity = new CityEntity();
        cityEntity.id = city.getId();
        cityEntity.name = city.getName();
        return cityEntity;
    }

    public City toModel(){
        return City.builder()
                .id(id)
                .name(name)
                .build();
    }
}
