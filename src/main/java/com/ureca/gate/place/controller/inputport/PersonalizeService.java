package com.ureca.gate.place.controller.inputport;

import java.util.List;

public interface PersonalizeService {
    List<String> getRecommendations(Long memberId, String city);
}
