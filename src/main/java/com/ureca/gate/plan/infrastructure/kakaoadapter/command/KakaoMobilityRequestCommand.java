package com.ureca.gate.plan.infrastructure.kakaoadapter.command;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoMobilityRequestCommand {

  private Origin origin;
  private List<Destination> destinations;
  private Integer radius;

  @Getter
  @Builder
  public static class Origin{
    private Double x;
    private Double y;
  }

  @Getter
  @Builder
  public static class Destination{
    private Double x;
    private Double y;
    private String key;
  }
}
