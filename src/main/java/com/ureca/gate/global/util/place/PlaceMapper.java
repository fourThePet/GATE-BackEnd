package com.ureca.gate.global.util.place;


import java.util.HashMap;
import java.util.Map;

public class PlaceMapper {

    public static Map<String, String> mapResponse(String response) {
        Map<String, String> result = new HashMap<>();

        // 응답을 줄 단위로 나누기
        String[] lines = response.split("\n");

        // 각 줄을 Key-Value로 변환
        for (String line : lines) {
            String[] parts = line.split(": ", 2); // ':' 기준으로 나누되 첫 번째만 분리
            if (parts.length == 2) {
                result.put(parts[0].trim(), parts[1].trim());
            }
        }
        return result;
    }
}
