package com.ureca.gate.place.application;

import com.ureca.gate.favorites.application.outputport.FavoritesRepository;
import com.ureca.gate.favorites.domain.Favorites;
import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.controller.inputport.PlaceDetailService;
import com.ureca.gate.place.controller.response.PlaceDetailResponse;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.domain.enumeration.YesNo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.PLACE_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceDetailServiceImpl implements PlaceDetailService {

    private final PlaceRepository placeRepository;
    private final FavoritesRepository favoritesRepository;
    @Override
    public PlaceDetailResponse getPlaceDetail(Long memberId,Long placeId) {
        Place place = placeRepository.findById(placeId).orElseThrow(()-> new BusinessException(PLACE_NOT_FOUND));
        YesNo isFavorite = (memberId != null && favoritesRepository.existsByMemberIdAndPlaceId(memberId, placeId))
                ? YesNo.Y
                : YesNo.N;
        
        return PlaceDetailResponse.from(place,isFavorite);
    }

}
