package com.example.demo.account.authentication.redis;

public interface RedisService {

    void setKeyAndValue(String token, Long accountId);
    Long getValueByKey(String token);
    void deleteByKey(String token);
}