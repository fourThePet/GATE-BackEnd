package com.ureca.gate.favorites.application;

import com.ureca.gate.favorites.application.command.FavoritesCommand;
import com.ureca.gate.favorites.application.outputport.FavoritesRepository;
import com.ureca.gate.favorites.controller.inputport.FavoritesService;
import com.ureca.gate.favorites.controller.response.FavoritesEnrollResponse;
import com.ureca.gate.favorites.controller.response.FavoritesResponse;
import com.ureca.gate.favorites.domain.Favorites;
import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.member.application.outputport.MemberRepository;
import com.ureca.gate.member.domain.Member;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.domain.enumeration.YesNo;
import com.ureca.gate.place.infrastructure.command.FavoritesCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;

    @Override
    @Transactional
    public FavoritesEnrollResponse create(Long memberId, Long placeId) {
        if(favoritesRepository.existsByMemberIdAndPlaceId(memberId,placeId)){
            throw new BusinessException(FAVORITES_ALREADY_EXISTS);
        }
        Member member = memberRepository.findById(memberId).orElseThrow(()->new BusinessException(MEMBER_NOT_FOUND));
        Place place = placeRepository.findById(placeId).orElseThrow(()->new BusinessException(PLACE_NOT_FOUND));
        Favorites favorites = Favorites.from(member,place);
        return FavoritesEnrollResponse.from(favoritesRepository.save(favorites));
    }

    @Override
    @Transactional
    public void delete(Long memberId, Long placeId) {
        Favorites favorites = favoritesRepository.findByMemberIdAndPlaceId(memberId,placeId).orElseThrow(()->new BusinessException(FAVORITES_NOT_FOUND));
        favoritesRepository.delete(favorites);
    }

    @Override
    public List<FavoritesResponse> getAll(FavoritesCommand favoritesCommand) {
        FavoritesCondition favoritesCondition = FavoritesCondition.from(favoritesCommand);
        return favoritesRepository.searchFavoritePlaces(favoritesCondition).stream()
                .map(FavoritesResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public YesNo checkIfFavorite(Long memberId, Long placeId) {
        if (memberId != null && favoritesRepository.existsByMemberIdAndPlaceId(memberId, placeId)) {
            return YesNo.Y;
        }
        return YesNo.N;
    }

    @Override
    public Integer countByPlaceId(Long placeId) {
        return favoritesRepository.countByPlaceId(placeId);
    }

}
