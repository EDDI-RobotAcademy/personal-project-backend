package com.example.demo.redis;

public interface RedisService {
    void setKeyAndValue(String userToken, String email);

    void deleteByKey(String token);

    String getValues(String email);
}
