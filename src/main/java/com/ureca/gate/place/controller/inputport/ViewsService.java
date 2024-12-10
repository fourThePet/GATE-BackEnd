package com.ureca.gate.place.controller.inputport;

import com.ureca.gate.place.domain.PopularPlace;

import java.util.List;

public interface ViewsService {
    void increaseViews(Long memberId, Long placeId);

    List<PopularPlace> getPopularPlaces(int limit);
}
