package com.ureca.gate.place.infrastructure.geoembedadapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "GeoEmbedClient",
        url = "http://43.201.31.245:5000")
public interface GeoEmbedClient {
    @PostMapping(value = "/embed")
    Map<String, Object> extractEmbeddingAndRegion(@RequestParam("query") String query);
}
