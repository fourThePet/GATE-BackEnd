package com.ureca.gate.favorites.application.command;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.favorites.controller.request.FavoritesRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FavoritesCommand {

    private final Long memberId;
    private final Long cityId;
    private final Size size;

    @Builder
    public FavoritesCommand(Long memberId, Long cityId, Size size) {
        this.memberId = memberId;
        this.cityId = cityId;
        this.size = size;
    }

    public static FavoritesCommand from(Long memberId, FavoritesRequest favoritesRequest) {
        return FavoritesCommand.builder()
                .memberId(memberId)
                .cityId(favoritesRequest.getCityId())
                .size(favoritesRequest.getSize())
                .build();
    }
}
