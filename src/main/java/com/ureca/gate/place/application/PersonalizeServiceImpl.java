package com.ureca.gate.place.application;

import com.ureca.gate.dog.application.outputport.DogRepository;
import com.ureca.gate.member.application.outputport.MemberRepository;
import com.ureca.gate.place.controller.inputport.PersonalizeService;
import com.ureca.gate.place.infrastructure.personalizeadpater.PersonalizeRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonalizeServiceImpl implements PersonalizeService {
    private final PersonalizeRequestBuilder personalizeRequestBuilder;
    private final DogRepository dogRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<String> getRecommendations(Long memberId, String city, String category) {

        String userId = Long.toString(memberId); // long값인 id를 sring으로 변환해야 함

        // 판단을 돕기 위한 메타데이터를 전송
        String gender = memberRepository.findById(memberId).get().getGender().toString().toLowerCase();
        String size = dogRepository.findByMemberId(memberId).get(0).getSize().toString().toLowerCase();

        // 해당 코드로 List<String>을 받아옴.
        return personalizeRequestBuilder.getRecommendations(userId, city, category,gender,size);
    }
}
