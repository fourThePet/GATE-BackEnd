package com.ureca.gate.place.application.outputport;

import com.ureca.gate.place.domain.City;

import java.util.List;

public interface CityRepository {
    List<City> findAll();
}
