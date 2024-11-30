package com.ureca.gate.favorites.application;

import com.ureca.gate.favorites.application.outputport.FavoritesRepository;
import com.ureca.gate.favorites.controller.inputport.FavoritesService;
import com.ureca.gate.favorites.controller.response.FavoritesResponse;
import com.ureca.gate.favorites.domain.Favorites;
import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.member.application.outputport.MemberRepository;
import com.ureca.gate.member.domain.Member;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.domain.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.MEMBER_NOT_FOUND;
import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.PLACE_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;

    @Override
    @Transactional
    public FavoritesResponse create(Long memberId, Long placeId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()->new BusinessException(MEMBER_NOT_FOUND));
        Place place = placeRepository.findById(placeId).orElseThrow(()->new BusinessException(PLACE_NOT_FOUND));
        Favorites favorites = Favorites.from(member,place);
        return FavoritesResponse.from(favoritesRepository.save(favorites));
    }

}
