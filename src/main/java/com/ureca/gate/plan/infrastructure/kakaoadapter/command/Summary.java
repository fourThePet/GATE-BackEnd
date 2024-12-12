package com.ureca.gate.plan.infrastructure.kakaoadapter.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Summary{

	@JsonProperty("distance")
	private final Integer distance;

	@JsonProperty("duration")
	private final Integer duration;

  public Summary(Integer distance, Integer duration) {
    this.distance = distance;
    this.duration = duration;
  }
}
