package com.ureca.gate.plan.domain;

import com.ureca.gate.plan.infrastructure.jpaadapter.command.PlanCommand;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class PlanInfo {
    private final Long id;
    private final String cityName;
    private final LocalDate date;
    private final Integer dogSize;

    public PlanInfo(Long id, String cityName, LocalDate date, Integer dogSize) {
        this.id = id;
        this.cityName = cityName;
        this.date = date;
        this.dogSize = dogSize;
    }

    public static List<PlanInfo> from(List<PlanCommand> planCommand) {
        return planCommand.stream()
                .map(PlanInfo::from)
                .toList();
    }

    public static PlanInfo from(PlanCommand planCommand) {
        return PlanInfo.builder()
                .id(planCommand.getId())
                .cityName(planCommand.getCityName())
                .date(planCommand.getDate())
                .dogSize(planCommand.getDogSize())
                .build();
    }
}
