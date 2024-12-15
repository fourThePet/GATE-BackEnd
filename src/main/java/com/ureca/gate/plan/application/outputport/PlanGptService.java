package com.ureca.gate.plan.application.outputport;

import com.ureca.gate.place.domain.Place;
import java.util.List;

public interface PlanGptService {

  List<Long> getRoute(List<Place> places);

}
