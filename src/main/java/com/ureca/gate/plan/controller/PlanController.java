package com.ureca.gate.plan.controller;

import com.ureca.gate.global.domain.CustomPage;
import com.ureca.gate.global.dto.response.PageResponse;
import com.ureca.gate.global.dto.response.SuccessResponse;
import com.ureca.gate.plan.application.command.PlanCreateCommand;
import com.ureca.gate.plan.application.command.PlanDeleteCommand;
import com.ureca.gate.plan.application.command.PlanListCommand;
import com.ureca.gate.plan.application.command.PlanUpdateCommand;
import com.ureca.gate.plan.controller.inputport.PlanRouteService;
import com.ureca.gate.plan.controller.inputport.PlanService;
import com.ureca.gate.plan.controller.request.PlanListRequest;
import com.ureca.gate.plan.controller.request.PlanCreateRequest;
import com.ureca.gate.plan.controller.request.PlanUpdateRequest;
import com.ureca.gate.plan.controller.response.PlanInfoResponse;
import com.ureca.gate.plan.controller.response.PlanResponse;
import com.ureca.gate.plan.domain.Plan;
import com.ureca.gate.plan.domain.PlanInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Plan API", description = "일정 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/plans")
@RestController
public class PlanController {

    private final PlanService planService;
    private final PlanRouteService planRouteService;

    @Operation(summary = "일정 리스트 조회 API", description = "현재 날짜를 기준으로 지난 여행, 다가오는 여행 리스트를 정렬조건에 따라 페이징")
    @GetMapping
    public SuccessResponse<PageResponse<PlanInfoResponse>> getAll(@AuthenticationPrincipal Long memberId,
                                                                  @ParameterObject PlanListRequest planListRequest) {
        PlanListCommand planListCommand = PlanListCommand.from(memberId, planListRequest);
        CustomPage<PlanInfo> planCustomPage = planService.searchPage(planListCommand);
        PageResponse<PlanInfoResponse> from = PageResponse.from(planCustomPage, PlanInfoResponse::from);
        return SuccessResponse.success(from);
    }

    @Operation(summary = "일정 조회 API", description = "생성된 일정의 세부 정보 조회")
    @GetMapping("/{planId}")
    public SuccessResponse<PlanResponse> getById(@AuthenticationPrincipal Long memberId,
                                                 @PathVariable("planId") Long planId) {
        Plan plan = planService.getById(planId);
        PlanResponse planResponse = PlanResponse.from(plan);
        return SuccessResponse.success(planResponse);
    }

    @Operation(summary = "일정 생성 API", description = "장소 아이디 리스트의 순서대로 일정 생성")
    @PostMapping
    public SuccessResponse<PlanResponse> create(@AuthenticationPrincipal Long memberId,
                                                @RequestBody PlanCreateRequest planCreateRequest) {
        PlanCreateCommand planCreateCommand = PlanCreateCommand.from(memberId, planCreateRequest);
        Plan plan = planService.create(planCreateCommand);
        PlanResponse planResponse = PlanResponse.from(plan);
        return SuccessResponse.success(planResponse);
    }

    @Operation(summary = "일정 수정 API")
    @PutMapping("/{planId}")
    public SuccessResponse<PlanResponse> update(@AuthenticationPrincipal Long memberId,
                                                @PathVariable Long planId,
                                                @RequestBody PlanUpdateRequest planSaveRequest) {
        PlanUpdateCommand planUpdateCommand = PlanUpdateCommand.from(memberId, planId, planSaveRequest);
        Plan plan = planService.update(planUpdateCommand);
        PlanResponse planResponse = PlanResponse.from(plan);
        return SuccessResponse.success(planResponse);
    }

    @Operation(summary = "일정 삭제 API")
    @DeleteMapping("/{planId}")
    public SuccessResponse<PlanResponse> delete(@AuthenticationPrincipal Long memberId,
                                                @PathVariable Long planId) {
        PlanDeleteCommand planDeleteCommand = PlanDeleteCommand.from(memberId, planId);
        planService.delete(planDeleteCommand);
        return SuccessResponse.successWithoutResult(null);
    }

    @Operation(summary = "일정 추천 경로 API", description = "일정 내 장소 간 최단경로 계산")
    @PostMapping("/route")
    public SuccessResponse<PlanResponse> createRoute(@AuthenticationPrincipal Long memberId, @RequestBody PlanCreateRequest planCreateRequest) {
        PlanCreateCommand planCreateCommand = PlanCreateCommand.from(memberId, planCreateRequest);
        Plan plan = planRouteService.createRoute(planCreateCommand);
        PlanResponse planResponse = PlanResponse.from(plan);
        return SuccessResponse.success(planResponse);
    }

    @Operation(summary = "GPT 일정 추천 경로 API")
    @PostMapping("/gpt-route")
    public SuccessResponse<PlanResponse> createGptRoute(@AuthenticationPrincipal Long memberId, @RequestBody PlanCreateRequest planCreateRequest) {
        PlanCreateCommand planCreateCommand = PlanCreateCommand.from(memberId, planCreateRequest);
        Plan plan = planRouteService.createGptRoute(planCreateCommand);
        PlanResponse planResponse = PlanResponse.from(plan);
        return SuccessResponse.success(planResponse);
    }
}
