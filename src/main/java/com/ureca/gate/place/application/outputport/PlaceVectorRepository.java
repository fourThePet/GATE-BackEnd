package com.ureca.gate.place.application.outputport;

import java.util.List;

public interface PlaceVectorRepository {
    List<Long> getTopPlaces(float[] embed);

    List<Long> findTop10SimilarPlaceIdsByRegionAndEmbedding(float[] embed,String city,String district,String tokwn);



}
