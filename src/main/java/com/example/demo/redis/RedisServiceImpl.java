package com.example.demo.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService{

    final private StringRedisTemplate redisTemplate;

    @Override
    public void setKeyAndValue(String token, String email) {
        String accountIdToString = String.valueOf(email);
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set(token, accountIdToString, Duration.ofMinutes(300000));
    }

    @Override
    public void deleteByKey(String token) {
        redisTemplate.delete(token);
    }

    @Override
    public String getValues(String token) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(token);
    }
}
