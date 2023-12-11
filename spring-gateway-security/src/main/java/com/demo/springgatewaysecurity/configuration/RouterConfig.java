package com.demo.springgatewaysecurity.configuration;

import com.demo.springgatewaysecurity.util.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * this is an important bean as it is responsible for redirecting the client to the appropriate math Service
 */
@Configuration
@Slf4j
public class RouterConfig {

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

    RouteLocator routeLocator = builder.routes()
        .route(AppConstants.MATH_SERVICE_KEY, r -> r.path("/api/calculator/**")
            .filters(f -> f.stripPrefix(2))
            .uri("lb://math-service")) // redirection based on service name
        .build();

    return routeLocator;
  }

}