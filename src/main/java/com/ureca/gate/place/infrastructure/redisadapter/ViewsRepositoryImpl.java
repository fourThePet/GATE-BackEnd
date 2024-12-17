package com.ureca.gate.place.infrastructure.redisadapter;

import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.application.outputport.ViewsRepository;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.domain.PopularPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
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
            redisTemplate.expire(zsetKey, Duration.ofHours(2));
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

    /**
     * 매 정시마다 이전 시간대의 데이터를 현 시간대에 복사 (최대 10개).
     */
    @Scheduled(cron = "0 0 * * * *") // 매 정시 실행
    public void copyPreviousHourData() {
        String currentTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH));
        String previousTimestamp = LocalDateTime.now().minusHours(1).format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH));

        String currentZSetKey = ZSET_KEY_PREFIX + currentTimestamp;
        String previousZSetKey = ZSET_KEY_PREFIX + previousTimestamp;

        // 이전 시간대의 상위 10개 데이터를 가져오기
        Set<ZSetOperations.TypedTuple<Object>> previousTopPlaces = redisTemplate.opsForZSet()
                .reverseRangeWithScores(previousZSetKey, 0, 9);

        if (previousTopPlaces == null || previousTopPlaces.isEmpty()) {
            return; // 이전 시간대 데이터가 없으면 종료
        }

        // 이전 데이터에서 가져온 항목을 현재 시간대에 추가 (score=0으로 설정)
        for (ZSetOperations.TypedTuple<Object> tuple : previousTopPlaces) {
            redisTemplate.opsForZSet().add(currentZSetKey, Objects.requireNonNull(tuple.getValue()), 0);
        }

        // Redis TTL 설정 (2시간)
        redisTemplate.expire(currentZSetKey, Duration.ofHours(2));
    }
}
