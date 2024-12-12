package com.ureca.gate.plan.infrastructure.kakaoadapter.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class KakaoMobilityResponseCommand {

  @JsonProperty("trans_id")
  private final String transId;

  @JsonProperty("routes")
  private final List<Route> routes;

  public KakaoMobilityResponseCommand(String transId, List<Route> routes) {
    this.transId = transId;
    this.routes = routes;
  }
}
