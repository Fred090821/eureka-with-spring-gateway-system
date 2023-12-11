package com.demo.mathmicroservice2.service;

import com.demo.mathmicroservice2.model.MultiplicationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * This service is the processing heart of the microservice its light and simple to support the design
 */
@Service
@Slf4j
public class MathServiceImpl implements MathService {

  // Environment is used to have access to environment variable.
  private final Environment environment;

  public MathServiceImpl(Environment environment) {
    this.environment = environment;
  }

  /**
   * This method multiply is the implementation of the interface multiply, it only purpose is to apply multiplication on
   * the 2 operands supplied via rest call
   *
   * @param firstOperand is the first integer operand
   * @param secondOperand is the second integer operand
   * @return the model that hold information about this server as in the port and the name that is registered with
   * eureka
   */
  @Override
  public MultiplicationResult multiply(int firstOperand, int secondOperand) {
    int result = firstOperand * secondOperand;
    int serverPort = environment.getProperty("server.port", Integer.class, 808001);
    String instanceName = environment.getProperty("spring.application.name", "DefaultInstance");

    log.debug("Multiplication operation: operand {} * operand {} = {}", firstOperand, secondOperand, result);

    return new MultiplicationResult(result, serverPort, instanceName);
  }
}
