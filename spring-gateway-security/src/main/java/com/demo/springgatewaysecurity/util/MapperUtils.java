package com.demo.springgatewaysecurity.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

/**
 * Convenient method for mapping object to a class type.
 */
@UtilityClass
public class MapperUtils {

  ObjectMapper mapper = new ObjectMapper();

  public <T> T objectMapper(Object object, Class<T> contentClassType) {
    return mapper.convertValue(object, contentClassType);
  }
}
