package com.ureca.gate.global.util.redis;

public class CacheKeyUtil {
    public static String getTokenKey(Long userId) {
        return "RT:" + userId;
    }

    public static String getLogoutKey(String token) {
        return "LOGOUT:" + token;
    }
}
