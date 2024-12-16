package com.ureca.gate.plan.application;

import com.ureca.gate.place.application.outputport.CityRepository;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.domain.City;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.plan.application.command.PlanCreateCommand;
import com.ureca.gate.plan.application.outputport.PlanGptService;
import com.ureca.gate.plan.controller.inputport.PlanRouteService;
import com.ureca.gate.plan.domain.Plan;
import com.ureca.gate.plan.infrastructure.kakaoadapter.KakaoMobilityClient;
import com.ureca.gate.plan.infrastructure.kakaoadapter.command.KakaoMobilityResponseCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanRouteServiceImpl implements PlanRouteService {

  @Value("${API-KEY.kakao}")
  private String kakaoApiKey;

  private static final int INF = Integer.MAX_VALUE;
  private static int n, statusFullBit;
  private static int[][] graph;
  private static int[][] dp;
  private static int[][] parent;
  private static int startNode;
  private static List<Integer> path;

  private final CityRepository cityRepository;
  private final PlaceRepository placeRepository;
  private final KakaoMobilityClient kakaoMobilityClient;
  private final PlanGptService planGptService;

  @Transactional
  public Plan createRoute(PlanCreateCommand planCreateCommand) {
    graph = createGraph(planCreateCommand);
    List<Long> placeIds = planCreateCommand.getPlaceIds();
    n = graph[0].length;
    startNode = n - 1;
    statusFullBit = (1 << n) - 1;
    dp = new int[n][1 << n];
    parent = new int[n][1 << n];

    // DP 테이블 초기화
    for (int i = 0; i < n; i++) {
      Arrays.fill(dp[i], -1);
      Arrays.fill(parent[i], -1);
    }

    int minDistance = tsp(startNode, 1 << startNode);
    path = new ArrayList<>();
    getPath();

    List<Place> places = new ArrayList<>();
    for (int i = 1; i < n; i++) {
      Long placeId = placeIds.get(path.get(i));
      Place place = placeRepository.getById(placeId);
      places.add(place);
    }

    City city = cityRepository.getById(planCreateCommand.getCityId());
    Plan plan = Plan.from(planCreateCommand, city, places);

    return plan;
  }

  @Transactional
  public Plan createGptRoute(PlanCreateCommand planCreateCommand) {
    List<Long> placeIds = planCreateCommand.getPlaceIds();
    List<Place> places = new ArrayList<>();
    for (Long placeId : placeIds){
      Place place = placeRepository.getById(placeId);
      places.add(place);
    }
    List<Long> GptRoute = planGptService.getRoute(places);
    List<Place> GptRoutePlaces = new ArrayList<>();
    for (Long placeId : GptRoute){
      Place place = placeRepository.getById(placeId);
      GptRoutePlaces.add(place);
    }
    City city = cityRepository.getById(planCreateCommand.getCityId());
    Plan plan = Plan.from(planCreateCommand, city, GptRoutePlaces);
    return plan;
  }

  @Transactional
  public KakaoMobilityResponseCommand getRouteResponse(Place origin, Place destination){
    Double originX = origin.getAddress().getLocationPoint().getX();
    Double originY = origin.getAddress().getLocationPoint().getY();
    String originPoint = originX + "," + originY;

    Double destinationX = destination.getAddress().getLocationPoint().getX();
    Double destinationY = destination.getAddress().getLocationPoint().getY();
    String destinationPoint = destinationX + "," + destinationY;

    String authorization = "KakaoAK " + kakaoApiKey;
    KakaoMobilityResponseCommand responseCommand = kakaoMobilityClient.getRoute(authorization, originPoint, destinationPoint, "DISTANCE");
    return responseCommand;
  }

  private int[][] createGraph(PlanCreateCommand planCreateCommand) {
    List<Long> placeIds = planCreateCommand.getPlaceIds();
    int cityCount = placeIds.size() + 1;
    int[][] graph = new int[cityCount][cityCount];

    for (int i = 0; i < cityCount - 1; i++) {
      Place origin = placeRepository.getById(placeIds.get(i));
      for (int j = i; j < cityCount - 1; j++) {
        if(i == j){
          graph[i][j] = 0;
        }else{
          Place destination = placeRepository.getById(placeIds.get(j));
          KakaoMobilityResponseCommand responseCommand = getRouteResponse(origin, destination);
          Integer distance = responseCommand.getRoutes().get(0).getSummary().getDistance();
          graph[i][j] = distance;
          graph[j][i] = distance;
        }
      }
    }
    return graph;
  }

  private int tsp(int current, int visited) {
    // 모든 도시 방문 완료
    if (visited == statusFullBit) {
      return graph[current][startNode]; // 시작점으로 돌아가는 비용 반환
    }

    // 이미 계산된 값이 있는 경우 반환
    if (dp[current][visited] != -1) return dp[current][visited];

    // 초기값 설정
    dp[current][visited] = INF;

    // 방문하지 않은 도시 탐색
    for (int i = 0; i < n; i++) {
      int next = visited | (1 << i); // 다음 방문 상태

      // 이미 방문한 도시거나 경로가 없으면 스킵
      if ((visited & (1 << i)) != 0 || current == i) continue;

      // 최소 비용 업데이트 및 경로 기록
      int newCost = tsp(i, next) + graph[current][i];
      if (dp[current][visited] > newCost) {
        dp[current][visited] = newCost;
        parent[current][visited] = i; // 다음 도시 기록
      }
    }

    return dp[current][visited];
  }

  private void getPath() {
    int current = startNode;
    int visited = 1 << startNode;

    // 시작 도시 추가
    path.add(current);

    while (true) {
      int next = parent[current][visited];
      if (next == -1)
        break;

      path.add(next);
      visited |= (1 << next);
      current = next;
    }
  }
}
