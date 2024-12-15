package com.ureca.gate.place.infrastructure.geoembedadapter;

import com.ureca.gate.place.application.outputport.GeoEmbedApiService;
import com.ureca.gate.place.infrastructure.command.GeoEmbed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GeoEmbedApiServiceImpl implements GeoEmbedApiService {
    private final GeoEmbedClient geoEmbedClient;

    @Override
    public GeoEmbed extractEmbeddingAndRegion(String query) {
        Map<String, Object> response = geoEmbedClient.extractEmbeddingAndRegion(query);

        // Extract embedding from response and convert it to float[] using a more efficient approach
        List<Double> embeddingList = (List<Double>) response.get("embedding");
        float[] embedding = new float[embeddingList.size()];
        for (int i = 0; i < embeddingList.size(); i++) {
            embedding[i] = embeddingList.get(i).floatValue();
        }

        String regions = (String) response.get("regions");

        return GeoEmbed.builder()
                .embedding(embedding)
                .regions(regions)
                .build();
    }
}


