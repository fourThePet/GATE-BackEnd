package com.ureca.gate.global.exception.custom;

import lombok.Getter;

@Getter
public class RouteNotFoundException extends RuntimeException {

  public RouteNotFoundException(String message) {
    super(message);
  }
}
