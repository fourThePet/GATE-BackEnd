package com.ureca.gate.plan.infrastructure.kakaoadapter.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Route {
	@JsonProperty("result_code")
	private final Integer resultCode;

	@JsonProperty("result_msg")
	private final String resultMsg;

	@JsonProperty("summary")
	private final Summary summary;

  public Route(Integer resultCode, String resultMsg, Summary summary) {
    this.resultCode = resultCode;
    this.resultMsg = resultMsg;
    this.summary = summary;
  }
}
