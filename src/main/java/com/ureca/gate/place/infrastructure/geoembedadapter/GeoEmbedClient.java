package com.ureca.gate.place.infrastructure.geoembedadapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "GeoEmbedClient",
        url = "http://localhost:5008")
public interface GeoEmbedClient {
    @PostMapping(value = "/embed")
    Map<String, Object> extractEmbeddingAndRegion(@RequestParam("query") String query);
}
