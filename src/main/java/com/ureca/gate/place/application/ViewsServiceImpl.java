package com.ureca.gate.place.application;

import com.ureca.gate.place.application.outputport.ViewsRepository;
import com.ureca.gate.place.controller.inputport.ViewsService;
import com.ureca.gate.place.domain.PopularPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ViewsServiceImpl implements ViewsService {

    private final ViewsRepository viewsRepository;

    @Override
    public List<PopularPlace> getPopularPlaces(int limit) {
        return viewsRepository.getPopularPlaces(limit);
    }
}
