package com.ureca.gate.place.infrastructure.redisadapter;

public final class RedisKeyConstants {

    private RedisKeyConstants() {
    }

    public static final String YYYY_MM_DD_HH = "yyyyMMddHH";
    public static final String ZSET_KEY_PREFIX = "place_views:";
    public static final String HASH_KEY_PREFIX = "popular_places:";
    public static final String HASH_PLACE_NAME = "placeName";
    public static final String HASH_CATEGORY_NAME = "categoryName";
    public static final String HASH_CITY_NAME = "cityName";
    public static final String HASH_PHOTO_URL = "photoUrl";
}
