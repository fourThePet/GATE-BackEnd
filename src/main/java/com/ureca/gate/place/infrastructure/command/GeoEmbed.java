package com.ureca.gate.place.infrastructure.command;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeoEmbed {
    private String regions; // 지역 정보
    private float[] embedding; // 임베딩 값
}
