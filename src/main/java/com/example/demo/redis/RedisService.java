package com.example.demo.redis;

public interface RedisService {
    void setKeyAndValue(String userToken, String email);

    Long getValueByKey(String userToken);

    void deleteByKey(String token);
}
