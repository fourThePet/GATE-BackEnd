package com.ureca.gate.place.application;

import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.application.outputport.ViewsRepository;
import com.ureca.gate.place.controller.inputport.ViewsService;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.domain.PopularPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ViewsServiceImpl implements ViewsService {

    private final ViewsRepository viewsRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public void increaseViews(Long memberId, Long placeId) {
        Place place = placeRepository.getById(placeId);
        viewsRepository.increaseViews(memberId, place);
    }

    @Override
    public List<PopularPlace> getPopularPlaces(int limit) {
        return viewsRepository.getPopularPlaces(limit);
    }
}
