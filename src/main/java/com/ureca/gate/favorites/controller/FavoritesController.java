package com.ureca.gate.favorites.controller;


import com.ureca.gate.favorites.controller.inputport.FavoritesService;
import com.ureca.gate.favorites.controller.request.FavoritesSaveRequest;
import com.ureca.gate.favorites.controller.response.FavoritesEnrollResponse;
import com.ureca.gate.favorites.controller.response.FavoritesResponse;
import com.ureca.gate.global.dto.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Favorites API", description = "즐겨찾기 API")
@RequestMapping("api/v1/favorites")
@RestController
@RequiredArgsConstructor
public class FavoritesController {
    private final FavoritesService favoritesService;
    @PostMapping("")
    @Operation(summary = "즐겨찾기 등록 API", description = "해당 장소 즐겨찾기 삭제 안하고 또 시도시 에러")
    public SuccessResponse<FavoritesEnrollResponse> create(@AuthenticationPrincipal Long memberId, @RequestBody FavoritesSaveRequest request){
        FavoritesEnrollResponse response = favoritesService.create(memberId,request.getPlaceId());
        return SuccessResponse.success(response);
    }

    @PatchMapping("/{favoriteId}")
    @Operation(summary = "즐겨찾기 삭제 API", description = "즐겨찾기 삭제 API")
    public SuccessResponse<Object> delete(@PathVariable Long favoriteId){
        favoritesService.delete(favoriteId);
        return SuccessResponse.successWithoutResult(null);
    }

    @GetMapping("")
    @Operation(summary = "즐겨찾기 리스트 조회 API", description = "나의 즐겨찾기 리스트 조회 API")
    public SuccessResponse<List<FavoritesResponse>> search(@AuthenticationPrincipal Long memberId){
        List<FavoritesResponse> responses = favoritesService.getAll(memberId);
        return SuccessResponse.success(responses);
    }



}
