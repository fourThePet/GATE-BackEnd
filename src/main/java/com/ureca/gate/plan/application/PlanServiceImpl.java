package com.ureca.gate.plan.application;

import com.ureca.gate.global.domain.CustomPage;
import com.ureca.gate.place.application.outputport.CityRepository;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.domain.City;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.plan.application.command.PlanCreateCommand;
import com.ureca.gate.plan.application.command.PlanDeleteCommand;
import com.ureca.gate.plan.application.command.PlanListCommand;
import com.ureca.gate.plan.application.command.PlanUpdateCommand;
import com.ureca.gate.plan.application.outputport.PlanDogRepository;
import com.ureca.gate.plan.application.outputport.PlanPlaceRepository;
import com.ureca.gate.plan.application.outputport.PlanRepository;
import com.ureca.gate.plan.controller.inputport.PlanService;
import com.ureca.gate.plan.domain.Plan;
import com.ureca.gate.plan.domain.PlanInfo;
import com.ureca.gate.plan.infrastructure.jpaadapter.command.PlanCommand;
import com.ureca.gate.plan.infrastructure.jpaadapter.command.PlanSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final CityRepository cityRepository;
    private final PlaceRepository placeRepository;
    private final PlanPlaceRepository planPlaceRepository;
    private final PlanDogRepository planDogRepository;

    @Transactional(readOnly = true)
    public CustomPage<PlanInfo> searchPage(PlanListCommand planListCommand) {
        PlanSearchCondition planSearchCondition = PlanSearchCondition.from(planListCommand);
        Pageable pageable = PageRequest.of(planListCommand.getPage(), planListCommand.getSize());
        CustomPage<PlanCommand> planCommandCustomPage = planRepository.searchPage(planSearchCondition, pageable);
        return CustomPage.convert(planCommandCustomPage, PlanInfo::from);
    }

    @Transactional(readOnly = true)
    public Plan getById(Long planId) {
        return planRepository.getById(planId);
    }

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

    @Transactional
    public Plan update(PlanUpdateCommand planUpdateCommand) {
        Plan plan = planRepository.getById(planUpdateCommand.getPlanId());

        planPlaceRepository.deleteAll(plan.getPlanPlaces());

        List<Place> places = planUpdateCommand.getPlaceIds()
                .stream()
                .map(placeRepository::getById)
                .toList();
        plan = plan.update(places);
        return planRepository.save(plan);
    }

    @Transactional
    public void delete(PlanDeleteCommand planDeleteCommand) {
        Plan plan = planRepository.getById(planDeleteCommand.getPlanId());
        planRepository.delete(plan);
        planDogRepository.deleteAll(plan.getPlanDogs());
        planPlaceRepository.deleteAll(plan.getPlanPlaces());
    }
}
