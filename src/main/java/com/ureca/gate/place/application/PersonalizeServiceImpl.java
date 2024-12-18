package com.ureca.gate.place.application;

import com.ureca.gate.dog.application.outputport.DogRepository;
import com.ureca.gate.global.dto.response.SliceResponse;
import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.global.exception.errorcode.CommonErrorCode;
import com.ureca.gate.member.application.outputport.MemberRepository;
import com.ureca.gate.place.application.outputport.CityRepository;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.controller.inputport.PersonalizeService;
import com.ureca.gate.place.controller.response.PlaceForPlanResponse;
import com.ureca.gate.place.infrastructure.command.PlaceForPlanCommand;
import com.ureca.gate.place.infrastructure.personalizeadpater.PersonalizeRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonalizeServiceImpl implements PersonalizeService {
    private final PersonalizeRequestBuilder personalizeRequestBuilder;
    private final DogRepository dogRepository;
    private final MemberRepository memberRepository;
    private final CityRepository cityRepository;
    private final PlaceRepository placeRepository;

    @Override
    public List<String> getRecommendations(Long memberId, String city) {

        String userId = Long.toString(memberId); // long값인 id를 sring으로 변환해야 함

        // 판단을 돕기 위한 메타데이터를 전송
        String gender = memberRepository.findById(memberId).get().getGender().toString().toLowerCase();
        String size = dogRepository.findByMemberId(memberId).get(0).getSize().toString().toLowerCase();

        // 해당 코드로 List<String>을 받아옴.
        return personalizeRequestBuilder.getRecommendations(userId, city,gender,size);
    }

    @Override
    public SliceResponse<PlaceForPlanResponse> getRecommendations(Long memberId, Long cityId, int page) {
        String userId = Long.toString(memberId); // long값인 id를 sring으로 변환해야 함
        String cityName = cityRepository.getById(cityId).getName();
        String gender = memberRepository.findById(memberId)
                .map(member -> member.getGender().toString().toLowerCase())
                .orElseThrow(() -> new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND));
        String size = dogRepository.findByMemberId(memberId).get(0).getSize().toString().toLowerCase();

        // 추천 장소 아이디 리스트
        List<Long> placeIds = personalizeRequestBuilder.getRecommendations(userId, cityName, gender, size)
                .stream()
                .map(Long::parseLong)
                .toList();

        // 페이징
        int start = page * 20;
        int end = Math.min(start + 20, placeIds.size());
        if (start >= placeIds.size()) {
            return new SliceResponse<>(List.of(), page, 20, false);
        }

        List<PlaceForPlanCommand> placeForPlanCommands = placeRepository.findPlaceForPlanResponseByIdIn(placeIds.subList(start, end));
        List<PlaceForPlanResponse> placeResponses = placeForPlanCommands.stream()
                .sorted(Comparator.comparing(place -> placeIds.indexOf(place.getPlaceId())))
                .map(PlaceForPlanResponse::from)
                .toList();

        boolean hasNext = end < placeIds.size();
        return new SliceResponse<>(placeResponses, page, 20, hasNext);
    }
}
