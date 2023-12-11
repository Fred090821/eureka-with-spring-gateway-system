package com.demo.springgatewaysecurity.util;

import com.demo.springgatewaysecurity.dto.ApiKey;
import com.demo.springgatewaysecurity.service.RedisCacheService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This component is meant to populate the redis databse
 */
@Component
public class DatabaseInitializer implements CommandLineRunner {

  private final RedisCacheService redisHashComponent;

  public DatabaseInitializer(RedisCacheService redisHashComponent) {
    this.redisHashComponent = redisHashComponent;
  }

  @Override
  public void run(String... args) throws Exception {
    List<ApiKey> apiKeys = new ArrayList<>();
    apiKeys.add(new ApiKey("343C-ED0B-4137-B27E", AppConstants.MATH_SERVICE_KEY));

    List<Object> lists = redisHashComponent.hValues(AppConstants.RECORD_KEY);
    if (lists.isEmpty()) {
      apiKeys.forEach(k -> redisHashComponent.hSet(AppConstants.RECORD_KEY, k.getKey(), k));
    }

  }
}