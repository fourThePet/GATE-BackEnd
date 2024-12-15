package com.ureca.gate.place.application.outputport;

import com.ureca.gate.place.infrastructure.command.GeoEmbed;

public interface GeoEmbedApiService {
    GeoEmbed extractEmbeddingAndRegion(String query);
}
