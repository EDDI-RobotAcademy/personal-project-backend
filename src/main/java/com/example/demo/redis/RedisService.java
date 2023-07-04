package com.example.demo.redis;

public interface RedisService {
    void setKeyAndValue(String userToken, Long accountId);

    Long getValueByKey(String userToken);

    void deleteByKey(String token);

}
