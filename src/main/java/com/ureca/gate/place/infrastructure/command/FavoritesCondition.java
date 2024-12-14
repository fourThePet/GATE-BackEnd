package com.ureca.gate.place.infrastructure.command;

import com.ureca.gate.favorites.application.command.FavoritesCommand;
import com.ureca.gate.dog.domain.enumeration.DogSizeGroup;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FavoritesCondition {

    private final Long memberId;
    private final Long cityId;
    private final List<String> allowSizes;

    @Builder
    public FavoritesCondition(Long memberId, Long cityId, List<String> allowSizes) {
        this.memberId = memberId;
        this.cityId = cityId;
        this.allowSizes = allowSizes;
    }

    public static FavoritesCondition from(FavoritesCommand favoritesCommand) {
        List<String> allowSizes = DogSizeGroup.allowSizesBySize(favoritesCommand.getSize());
        return FavoritesCondition.builder()
                .memberId(favoritesCommand.getMemberId())
                .cityId(favoritesCommand.getCityId())
                .allowSizes(allowSizes)
                .build();
    }
}
