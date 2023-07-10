package com.example.demo.authentication.redis;

public interface RedisService {

    void setKeyAndValue(String token, Long accountId);
    String getValueByToken(String token);
    void deleteByKey(String token);
    void registBlackList(String token, Long expTime);
}