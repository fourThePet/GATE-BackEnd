package com.ureca.gate.plan.application;

import com.ureca.gate.place.application.outputport.CityRepository;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.domain.City;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.plan.application.command.PlanCreateCommand;
import com.ureca.gate.plan.application.outputport.PlanRepository;
import com.ureca.gate.plan.controller.inputport.PlanService;
import com.ureca.gate.plan.domain.Plan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final CityRepository cityRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public Plan create(PlanCreateCommand planCreateCommand) {
        City city = cityRepository.getById(planCreateCommand.getCityId());
        List<Place> places = planCreateCommand.getPlaceIds()
                .stream()
                .map(placeRepository::getById)
                .toList();
        Plan plan = Plan.from(planCreateCommand, city, places);
        return planRepository.save(plan);
    }
}
