package com.ureca.gate.place.infrastructure.redisadapter;

import com.ureca.gate.place.application.outputport.ViewsRepository;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.domain.PopularPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class ViewsRepositoryImpl implements ViewsRepository {

    public static final String YYYY_MM_DD_HH = "yyyyMMddHH";
    public static final String ZSET_KEY_PREFIX = "place_views:";
    public static final String HASH_KEY_PREFIX = "popular_places:";
    public static final String HASH_PLACE_NAME = "placeName";
    public static final String HASH_CATEGORY_NAME = "categoryName";
    public static final String HASH_CITY_NAME = "cityName";
    public static final String HASH_PHOTO_URL = "photoUrl";

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void increaseViews(Long memberId, Place place) {
        Long placeId = place.getId();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH));
        String zsetKey = ZSET_KEY_PREFIX + timestamp;
        String memberKey = timestamp + ":" + placeId + ":" + memberId;
        String hashKey = HASH_KEY_PREFIX + timestamp + ":" + placeId;

        // 중복 방지 (String)
        boolean existMember = redisTemplate.opsForValue().get(memberKey) != null;
        if (existMember) {
            return;
        }
        redisTemplate.opsForValue().set(memberKey, true, Duration.ofHours(1));

        // 조회수 증가 (ZSET)
        redisTemplate.opsForZSet().incrementScore(zsetKey, placeId, 1);
        redisTemplate.expire(zsetKey, Duration.ofHours(1));

        // 장소 정보 저장 (HASH)
        if (redisTemplate.opsForHash().keys(hashKey).isEmpty()) {
            redisTemplate.opsForHash().put(hashKey, HASH_PLACE_NAME, place.getName());
            redisTemplate.opsForHash().put(hashKey, HASH_CATEGORY_NAME, place.getCategory().getName());
            redisTemplate.opsForHash().put(hashKey, HASH_CITY_NAME, place.getAddress().getCity().getName());
            redisTemplate.opsForHash().put(hashKey, HASH_PHOTO_URL, place.getPhotoUrl());
            redisTemplate.expire(hashKey, Duration.ofHours(1));
        }
    }

    @Override
    public List<PopularPlace> getPopularPlaces(int limit) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH));
        String zsetKey = ZSET_KEY_PREFIX + timestamp;

        // ZSET에서 1부터 limit까지 조회
        Set<ZSetOperations.TypedTuple<Object>> topPlaces = redisTemplate.opsForZSet().reverseRangeWithScores(zsetKey, 0, limit - 1);
        if (topPlaces == null || topPlaces.isEmpty()) {
            return Collections.emptyList();
        }
        return topPlaces.stream()
                .map(tuple -> {
                    String placeId = (String) tuple.getValue();
                    String hashKey = HASH_KEY_PREFIX + timestamp + ":" + placeId;
                    return getPopularPlace(placeId, hashKey);
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private PopularPlace getPopularPlace(String placeId, String hashKey) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(hashKey);
        if (entries.isEmpty()) {
            return null;
        }
        return PopularPlace.builder()
                .placeId(Long.parseLong(placeId))
                .placeName((String) entries.get(HASH_PLACE_NAME))
                .categoryName((String) entries.get(HASH_CATEGORY_NAME))
                .cityName((String) entries.get(HASH_CITY_NAME))
                .photoUrl((String) entries.get(HASH_PHOTO_URL))
                .build();
    }
}
