package com.ureca.gate.place.controller.response;

import com.ureca.gate.place.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceCategoryResponse {
    @Schema(description = "장소 카테고리 아이디", example = "1")
    private Long id;
    @Schema(description = "장소 카테고리 이름", example = "카페")
    private String name;

    public static PlaceCategoryResponse from(Category category){
        return PlaceCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
