package com.ureca.gate.favorites.controller;


import com.ureca.gate.favorites.controller.inputport.FavoritesService;
import com.ureca.gate.favorites.controller.request.FavoritesSaveRequest;
import com.ureca.gate.favorites.controller.response.FavoritesResponse;
import com.ureca.gate.global.dto.response.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Favorites API", description = "즐겨찾기 API")
@RequestMapping("api/v1/favorites")
@RestController
@RequiredArgsConstructor
public class FavoritesController {
    private final FavoritesService favoritesService;
    @PostMapping("")
    public SuccessResponse<FavoritesResponse> create(@AuthenticationPrincipal Long memberId, @RequestBody FavoritesSaveRequest request){
        System.out.println(memberId);
        FavoritesResponse response = favoritesService.create(memberId,request.getPlaceId());
        return SuccessResponse.success(response);
    }

}
