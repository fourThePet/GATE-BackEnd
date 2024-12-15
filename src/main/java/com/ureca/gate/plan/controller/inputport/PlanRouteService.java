package com.ureca.gate.plan.controller.inputport;

import com.ureca.gate.place.domain.Place;
import com.ureca.gate.plan.application.command.PlanCreateCommand;
import com.ureca.gate.plan.domain.Plan;
import com.ureca.gate.plan.infrastructure.kakaoadapter.command.KakaoMobilityResponseCommand;

public interface PlanRouteService {

  KakaoMobilityResponseCommand getRouteResponse(Place origin, Place destination);

  Plan createRoute(PlanCreateCommand planCreateCommand);

  Plan createGptRoute(PlanCreateCommand planCreateCommand);
}
