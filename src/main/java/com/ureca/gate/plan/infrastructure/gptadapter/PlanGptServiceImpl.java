package com.ureca.gate.plan.infrastructure.gptadapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.plan.application.outputport.PlanGptService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanGptServiceImpl implements PlanGptService {

  private final ChatClient chatClient;

  @Override
  public List<Long> getRoute(List<Place> places) {
    String inputData = places.stream().map(place -> String.format("장소ID: %d, 장소명: %s, 주소: %s, 카테고리: %s", place.getId(), place.getName(), place.getAddress().getRoadAddress(), place.getCategory().getName())).collect(Collectors.joining("\n"));

    String response = chatClient.prompt()
        .system("""
            1. 제공해야 하는 기능
              - 반려견과 하루 동안 함께 여행할 수 있는 일정을 추천해주는 기능을 제공해야 합니다.
              - 여행은 하나의 도시 내에서만 진행되며, 사용자가 입력한 장소를 기반으로 일정을 구성해야 합니다.
            2. 입력 데이터
              - 사용자가 선택한 장소 목록이 입력으로 주어집니다.
              - 장소 목록의 각 장소는 다음 정보를 포함합니다:
                - 장소ID (고유 식별자)
                - 장소명
                - 주소
                - 카테고리
              - 장소 목록에는 최소 1개에서 최대 10개까지 장소가 포함될 수 있습니다.
              - 주어지는 장소는 모두 반려견 동반이 가능한 시설입니다.
              - 입력 데이터는 String 타입으로 주어집니다.
              - 입력 데이터 예시
                  - 장소ID: 1, 장소명: 멋진공원, 주소: 서울시 강남구 도산대로 123, 카테고리: 여행지
                  - 장소ID: 2, 장소명: 멋진카페, 주소: 서울시 강남구 도산대로 345, 카테고리: 카페
                  - 장소ID: 3, 장소명: 멋진식당, 주소: 서울시 강남구 도산대로 678, 카테고리: 식당
              - 장소 카테고리는 총 8개가 주어지고 목록은 아래와 같습니다.
                - 식당
                - 카페
                - 여행지
                - 펜션
                - 호텔
                - 박물관
                - 미술관
                - 문예회관
            3. 기대하는 결과
              - 주어진 장소 정보를 바탕으로 최적의 일정을 생성해내야 하고 아래의 요구사항을 만족해야 합니다.
                - 사용자가 입력한 모든 장소를 방문해야 합니다.
                - 사용자가 입력한 장소 순서는 생성하는 일정에 어떠한 영향도 주지 않습니다.
                - 장소 간 이동 시간을 고려하여 효율적인 일정을 생성해야 합니다.
                - 일정은 09:00에 시작해서 20:00에 종료하는 것을 기본으로 합니다.
                - 장소 방문 순서는 일반적인 여행 동선을 따릅니다.
                  - 식당에서 식사 후 카페를 방문하도록 합니다.
                  - 같은 카테고리의 장소는 연달아 방문하지 않습니다.
                - 그러나 사용자가 같은 카테고리의 장소만 입력했을 경우에도 일정은 항상 생성해야 합니다.
                - 입력한 장소 목록이 불충분하거나 주어진 장소 목록만으로 적합한 일정을 짜기 어려운 경우에도 반드시 일정을 생성합니다.
                - 사용자가 이동하는 수단은 항상 사용자의 자동차라고 가정합니다.
                - 사용자가 입력한 장소는 반드시 한번씩만 방문합니다.
                - 일정에서 같은 장소를 두 번 이상 방문할 수 없습니다.
            4. 최종 출력
              - 최종 출력은 생성한 일정의 장소 순서대로 반드시 장소ID만 나열한 리스트를 출력합니다.
              - 장소ID 리스트 이외에 다른 내용은 어떠한 것도 출력하지 않습니다.
              - 입력으로 들어온 장소개수와 출력으로 내보내는 장소ID 리스트의 길이는 반드시 같습니다.
              - 출력하는 장소ID 리스트의 장소ID는 절대로 중복이 존재하면 안됩니다.
              - 최종 출력 예시
                - [1, 2, 3]
              - 틀린 최종 출력 예시
                - [1 ,2, 3, 2, 1]
            """)
        .user(inputData)
        .call()
        .content();

    return parsePlaceIds(response);
  }

  private List<Long> parsePlaceIds(String response) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      List<String> stringIds = objectMapper.readValue(response, new TypeReference<List<String>>() {});
      return stringIds.stream()
          .map(Long::valueOf) // String -> Long 변환
          .collect(Collectors.toList());
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse place IDs from response: " + response, e);
    }
  }

}
