package com.ureca.gate.global.infrastructure.cache;

public interface CacheRepository {
    public void setData(String key, String value, Long exprTime);

    public String getData(String key);

    public void deleteData(String key);
}
