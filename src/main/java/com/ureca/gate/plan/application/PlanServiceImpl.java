package com.ureca.gate.plan.application;

import com.ureca.gate.plan.application.command.PlanCreateCommand;
import com.ureca.gate.plan.application.outputport.PlanRepository;
import com.ureca.gate.plan.controller.inputport.PlanService;
import com.ureca.gate.plan.domain.Plan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    @Override
    public Plan create(PlanCreateCommand planCreateCommand) {
        Plan plan = Plan.from(planCreateCommand);
        return planRepository.save(plan);
    }
}
