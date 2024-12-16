package com.ureca.gate.global.util.city;

import java.util.HashMap;
import java.util.Map;

public class CityMapper {
    private static final Map<Long, String> cityMap = new HashMap<>();

    static {
        cityMap.put(1L, "경기도");
        cityMap.put(2L, "서울특별시");
        cityMap.put(3L, "인천광역시");
        cityMap.put(4L, "경상북도");
        cityMap.put(5L, "대구광역시");
        cityMap.put(6L, "전라북도");
        cityMap.put(7L, "경상남도");
        cityMap.put(8L, "전라남도");
        cityMap.put(9L, "부산광역시");
        cityMap.put(10L, "광주광역시");
        cityMap.put(11L, "강원도");
        cityMap.put(12L, "대전광역시");
        cityMap.put(13L, "충청북도");
        cityMap.put(14L, "제주특별자치도");
        cityMap.put(15L, "충청남도");
        cityMap.put(16L, "울산광역시");
        cityMap.put(17L, "세종");
    }

    public static String getCityName(Long cityId) {
        return cityMap.getOrDefault(cityId, "Unknown");
    }
}