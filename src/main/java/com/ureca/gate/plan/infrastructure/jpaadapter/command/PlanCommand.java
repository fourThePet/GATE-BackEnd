package com.ureca.gate.plan.infrastructure.jpaadapter.command;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PlanCommand {

    private final Long id;
    private final String cityName;
    private final String cityPhotoUrl;
    private final LocalDate date;
    private final Integer dogSize;

    @QueryProjection
    public PlanCommand(Long id, String cityName, String cityPhotoUrl, LocalDate date, Integer dogSize) {
        this.id = id;
        this.cityName = cityName;
        this.cityPhotoUrl = cityPhotoUrl;
        this.date = date;
        this.dogSize = dogSize;
    }
}
