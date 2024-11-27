package com.ureca.gate.place.application;

import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.controller.inputport.PlaceDetailService;
import com.ureca.gate.place.controller.response.PlaceDetailResponse;
import com.ureca.gate.place.domain.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.PLACE_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceDetailServiceImpl implements PlaceDetailService {

    private final PlaceRepository placeRepository;
    @Override
    public PlaceDetailResponse getPlaceDetail(Long placeId) {
        Place place = placeRepository.findById(placeId).orElseThrow(()-> new BusinessException(PLACE_NOT_FOUND));
        return PlaceDetailResponse.from(place);
    }

}
