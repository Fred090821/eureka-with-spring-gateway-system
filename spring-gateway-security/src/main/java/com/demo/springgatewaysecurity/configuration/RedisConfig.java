package com.demo.springgatewaysecurity.configuration;

import com.demo.springgatewaysecurity.util.CustomRedisSerializer;
import java.util.Objects;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Additional redis configurations like serialization, LettuceConnectionFactory and RedisTemplate.
 */
@Configuration
public class RedisConfig implements CachingConfigurer {

  private final Environment env;
  private final GenericObjectPoolConfig genericObjectPoolConfig;

  @Autowired
  public RedisConfig(Environment env, GenericObjectPoolConfig genericObjectPoolConfig) {
    this.env = env;
    this.genericObjectPoolConfig = genericObjectPoolConfig;
  }

  @Bean
  public LettucePoolingClientConfiguration poolingClientConfiguration() {

    return LettucePoolingClientConfiguration.builder()
        .poolConfig(genericObjectPoolConfig)
        .build();
  }


  private RedisStandaloneConfiguration redisStandaloneConfiguration() {
    RedisStandaloneConfiguration redisStandaloneConfiguration
        = new RedisStandaloneConfiguration(Objects.requireNonNull(env.getProperty("spring.data.redis.host")),
        Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.data.redis.port"))));
    return redisStandaloneConfiguration;
  }

  @Bean(name = "customConnectionFactory")
  public LettuceConnectionFactory connectionFactory(
      LettucePoolingClientConfiguration poolingClientConfiguration) {

    return new LettuceConnectionFactory(redisStandaloneConfiguration(), poolingClientConfiguration);

  }

  @Bean(name = "customRedisTemplate")
  public RedisTemplate<Object, Object> customRedisTemplate(
      @Qualifier("customConnectionFactory") RedisConnectionFactory connectionFactory) {

    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);
    redisTemplate.setDefaultSerializer(new CustomRedisSerializer());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new CustomRedisSerializer());
    redisTemplate.setHashValueSerializer(new CustomRedisSerializer());
    return redisTemplate;
  }

}