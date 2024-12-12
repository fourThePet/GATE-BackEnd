package com.ureca.gate.place.application.outputport;

import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.domain.PopularPlace;

import java.util.List;

public interface ViewsRepository {
    void increaseViews(Long memberId, Place placeId);

    List<PopularPlace> getPopularPlaces(int limit);
}
