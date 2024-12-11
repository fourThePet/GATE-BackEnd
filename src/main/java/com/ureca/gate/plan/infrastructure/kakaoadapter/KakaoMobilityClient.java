package com.ureca.gate.plan.infrastructure.kakaoadapter;

import com.ureca.gate.plan.infrastructure.kakaoadapter.command.KakaoMobilityRequestCommand;
import com.ureca.gate.plan.infrastructure.kakaoadapter.command.KakaoMobilityResponseCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "KakaoMobilityClient",
    url = "https://apis-navi.kakaomobility.com/v1"
)
public interface KakaoMobilityClient {

  @Transactional
  @PostMapping("/destinations/directions")
  KakaoMobilityResponseCommand getRoutes(@RequestHeader("Authorization") String authorization, @RequestBody KakaoMobilityRequestCommand request);

  @GetMapping("/directions")
  KakaoMobilityResponseCommand getRoute(
      @RequestHeader("Authorization") String authorization,
      @RequestParam("origin") String origin,
      @RequestParam("destination") String destination,
      @RequestParam("priority") String priority);
}
