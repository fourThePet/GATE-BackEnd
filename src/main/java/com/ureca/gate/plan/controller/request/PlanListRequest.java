package com.ureca.gate.plan.controller.request;

import com.ureca.gate.plan.domain.DateFilter;
import com.ureca.gate.plan.domain.SortOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PlanListRequest {

    @Schema(description = "AFTER: 다가오는 여행, BEFORE: 지난 여행", example = "AFTER")
    private final DateFilter dateFilter;

    @Schema(description = "ASC: 오름차순, DESC: 내림차순", example = "ASC")
    private final SortOrder sortOrder;

    @Schema(description = "페이지 번호, 0 부터 시작", example = "0")
    private final int page;

    @Schema(description = "한 페이지에 포함될 항목(데이터)의 개수, 최소 1 이상", example = "10")
    private final int size;

    public PlanListRequest(DateFilter dateFilter, SortOrder sortOrder, int page, int size) {
        this.dateFilter = dateFilter;
        this.sortOrder = sortOrder;
        this.page = page;
        this.size = size;
    }
}
