package com.demo.springgatewaysecurity.service;

import com.demo.springgatewaysecurity.util.MapperUtils;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Component used to save and retrieve entries from redis database
 */
@Service
@Slf4j
public class RedisCacheService {

  private final RedisTemplate<Object, Object> redisTemplate;

  @Autowired
  public RedisCacheService(
      @Qualifier("customRedisTemplate") RedisTemplate<Object, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void hSet(String key, Object hashKey, Object value) {
    log.info("add an entry to the redis with key {}", key);
    Map ruleHash = MapperUtils.objectMapper(value, Map.class);
    redisTemplate.opsForHash().put(key, hashKey, ruleHash);
  }

  public List<Object> hValues(String key) {
    log.info("retrieve all object mapped to the key {}", key);
    return redisTemplate.opsForHash().values(key);
  }

  public Object hGet(String key, Object hashKey) {
    return redisTemplate.opsForHash().get(key, hashKey);
  }
}