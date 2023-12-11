package com.demo.springgatewaysecurity.configuration;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * One of the configuration for Redis
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "common.redis.pool-config", ignoreInvalidFields = true)
public class ConnectionPoolProperties {

  @Value("${maxIdle:64}")
  private Integer maxIdle;

  @Value("${maxTotal:64}")
  private Integer maxTotal;

  @Value("${minIdle:8}")
  private Integer minIdle;

  @Bean
  public GenericObjectPoolConfig genericObjectPoolConfig() {
    GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

    // This is the max number of connections that can be idle in the pool
    // without being immediately evicted (closed). If not set, the default value is 8
    poolConfig.setMaxIdle(maxIdle);

    // Simultaneous connection by Redis nodes
    poolConfig.setMaxTotal(maxTotal);
    poolConfig.setMinIdle(minIdle);
    return poolConfig;
  }
}