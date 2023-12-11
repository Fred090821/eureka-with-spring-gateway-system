package com.demo.springgatewaysecurity.filter;


import com.demo.springgatewaysecurity.dto.ApiKey;
import com.demo.springgatewaysecurity.service.RedisCacheService;
import com.demo.springgatewaysecurity.util.AppConstants;
import com.demo.springgatewaysecurity.util.MapperUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

  private final RedisCacheService redisHashComponent;

  public AuthFilter(RedisCacheService redisHashComponent) {
    this.redisHashComponent = redisHashComponent;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    log.info(" ********* Start key validation for path {}", exchange.getRequest().getPath());

    List<String> apiKeyHeader = exchange.getRequest().getHeaders().get("Authorization");

    String[] parts;
    if (apiKeyHeader != null && !apiKeyHeader.isEmpty()) {
      parts = apiKeyHeader.get(0).split(" ");

      if (parts.length != 2 || !parts[0].equalsIgnoreCase("Bearer")) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
            "you can't consume this service , Please validate your apikeys");
      }

      Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
      String routeId = route != null ? route.getId() : null;

      if (routeId == null || CollectionUtils.isEmpty(apiKeyHeader) || !isAuthorize(routeId, parts[1])) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
            "you can't consume this service , Please validate your apikeys");
      }
    }

    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }

  private boolean isAuthorize(String routeId, String apiKey) {

    log.info("ROUTE ID...{}", routeId);

    Object apiKeyObject = redisHashComponent.hGet(AppConstants.RECORD_KEY, apiKey);
    if (apiKeyObject != null) {
      ApiKey key = MapperUtils.objectMapper(apiKeyObject, ApiKey.class);
      final boolean isAuthorization = key.getServices().contains(routeId);

      log.info("is Api key Authorized?...{}", isAuthorization);

      return key.getServices().contains(routeId);
    } else {
      return false;
    }
  }
}
