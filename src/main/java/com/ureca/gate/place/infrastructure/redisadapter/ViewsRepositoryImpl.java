package com.ureca.gate.place.infrastructure.redisadapter;

import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.application.outputport.ViewsRepository;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.domain.PopularPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.ureca.gate.place.infrastructure.redisadapter.RedisKeyConstants.*;

@RequiredArgsConstructor
@Repository
public class ViewsRepositoryImpl implements ViewsRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final PlaceRepository placeRepository;

    @Transactional
    public void increaseViews(Long memberId, Place place) {
        Long placeId = place.getId();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH));
        String zsetKey = ZSET_KEY_PREFIX + timestamp;
        String memberKey = timestamp + ":" + placeId + ":" + memberId;

        // 중복 방지 (String)
        boolean existMember = redisTemplate.opsForValue().get(memberKey) != null;
        if (existMember) {
            return;
        }
        redisTemplate.opsForValue().set(memberKey, true, Duration.ofHours(1));

        // 조회수 증가 (ZSET)
        Long zsetTtl = redisTemplate.getExpire(zsetKey);
        if (zsetTtl == null || zsetTtl < 0) {
            redisTemplate.expire(zsetKey, Duration.ofHours(1));
        }
        redisTemplate.opsForZSet().incrementScore(zsetKey, placeId, 1);
    }

    @Transactional(readOnly = true)
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
                    String value = (String) tuple.getValue();
                    long placeId = Long.parseLong(Objects.requireNonNull(value));
                    return getPopularPlace(placeId);
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private PopularPlace getPopularPlace(Long placeId) {
        return placeRepository.findById(placeId)
                .map(PopularPlace::from)
                .orElse(null);
    }
}
