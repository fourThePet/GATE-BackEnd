package com.ureca.gate.place.application;

import com.ureca.gate.place.application.outputport.PlaceCategoryRepository;
import com.ureca.gate.place.controller.inputport.PlaceCategoryService;
import com.ureca.gate.place.controller.response.PlaceCategoryResponse;
import com.ureca.gate.place.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceCategoryServiceImpl implements PlaceCategoryService {

    private final PlaceCategoryRepository placeCategoryRepository;
    @Override
    public List<PlaceCategoryResponse> getPlaceCategories() {
        return placeCategoryRepository.findByAll().stream()
                .map(PlaceCategoryResponse::from)
                .toList();
    }


}
