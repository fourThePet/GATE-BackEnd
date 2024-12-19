package com.ureca.gate.place.infrastructure.personalizeadpater;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsRequest;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsResponse;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PersonalizeRequestBuilder {

    private final PersonalizeRuntimeClient personalizeClient;

    @Value("${spring.cloud.aws.campaignArn}")
    private String campaignArn;

    @Value("${spring.cloud.aws.filterArn}")
    private String filterArn;

    public GetRecommendationsRequest buildRecommendationsRequest(
            String campaignArn, String filterArn,
            String userId, String city, String gender, String size) {

        Map<String, String> filterValues = new HashMap<>();
        if (city != null) filterValues.put("CITY1", String.format("\"%s\"", city));
        if (city != null) filterValues.put("CITY2", String.format("\"%s\"", city));
        if (city != null) filterValues.put("CITY3", String.format("\"%s\"", city));
        if (city != null) filterValues.put("CITY4", String.format("\"%s\"", city));
        if (city != null) filterValues.put("CITY5", String.format("\"%s\"", city));

        filterValues.put("CATEGORY1", "\"카페\"");
        filterValues.put("CATEGORY2", "\"식당\"");
        filterValues.put("CATEGORY3", "\"문화시설\"");
        filterValues.put("CATEGORY4", "\"숙소\"");
        filterValues.put("CATEGORY5", "\"여행지\"");

        Map<String, String> context = new HashMap<>();
        if(gender != null) context.put("gender", String.format("\"%s\"", gender));
        if(size != null) context.put("size", String.format("\"%s\"", size));

        return GetRecommendationsRequest.builder()
                .campaignArn(campaignArn)
                .filterArn(filterArn)
                .filterValues(filterValues)
                .context(context)
                .userId(userId)
                .build();
    }

    public List<String> getRecommendations(
            String userId, String city, String gender, String size) {

        GetRecommendationsRequest request = buildRecommendationsRequest(
                campaignArn, filterArn, userId, city, gender, size);

        GetRecommendationsResponse response = personalizeClient.getRecommendations(request);

        // 추천된 아이템 ID 리스트 반환
        return response.itemList().stream()
                .map(PredictedItem::itemId).toList();
    }
}
