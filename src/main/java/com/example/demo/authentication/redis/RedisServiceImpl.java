package com.example.demo.authentication.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    final private StringRedisTemplate redisTemplate;

    @Override
    public void setKeyAndValue(String token, Long accountId) {
        String accountIdToString = String.valueOf(accountId);
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set(token, accountIdToString, Duration.ofMinutes(1440));
    }

    @Override
    public String getValueByToken(String token) {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        String tokenValue = value.get(token);

        return tokenValue;
    }

    @Override
    public void deleteByKey(String token) {
        redisTemplate.delete(token);
    }

    @Override
    public void registBlackList(String token, Long expTime) {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set(token, "LogOut", Duration.ofMinutes(expTime));
    }
}