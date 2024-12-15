package com.ureca.gate.place.application;

import com.ureca.gate.place.application.outputport.CityRepository;
import com.ureca.gate.place.controller.inputport.CityListService;
import com.ureca.gate.place.controller.response.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityListServiceImpl implements CityListService {

    private final CityRepository cityRepository;

    @Override
    public List<CityResponse> getCityList() {
        return cityRepository.findAll().stream()
                .map(CityResponse::from)
                .collect(Collectors.toList());
    }
}
