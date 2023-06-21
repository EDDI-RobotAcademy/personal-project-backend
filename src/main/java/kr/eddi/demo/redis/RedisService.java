package kr.eddi.demo.redis;

public interface RedisService {
    void setKeyAndValue(String token, Long accountId);
    Long getValueByKey(String token);
    void deleteByKey(String token);
}
