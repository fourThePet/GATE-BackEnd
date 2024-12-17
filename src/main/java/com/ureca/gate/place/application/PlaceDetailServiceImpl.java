package com.ureca.gate.place.application;

import com.ureca.gate.favorites.controller.inputport.FavoritesService;
import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.application.outputport.ViewsRepository;
import com.ureca.gate.place.controller.inputport.PlaceDetailService;
import com.ureca.gate.place.controller.response.PlaceDetailResponse;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.domain.enumeration.YesNo;
import com.ureca.gate.review.application.outputport.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.PLACE_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceDetailServiceImpl implements PlaceDetailService {

    private final PlaceRepository placeRepository;
    private final FavoritesService favoritesService;
    private final ViewsRepository viewsRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public PlaceDetailResponse getPlaceDetail(Long memberId,Long placeId,Double latitude,Double longitude) {
        Place place = placeRepository.findById(placeId).orElseThrow(()-> new BusinessException(PLACE_NOT_FOUND));


        Integer reviewNum = reviewRepository.CountByPlace(place);
        Double starAvg = reviewRepository.findAverageStarRateByPlaceId(placeId);

        YesNo isFavorite = favoritesService.checkIfFavorite(memberId,placeId);
        Integer favoritesNum = favoritesService.countByPlaceId(placeId);

        viewsRepository.increaseViews(memberId, place);

        Double distance = placeRepository.calculrateDistance(longitude,latitude,placeId);

        return PlaceDetailResponse.from(place,favoritesNum,isFavorite,reviewNum,starAvg,distance);
    }

}
