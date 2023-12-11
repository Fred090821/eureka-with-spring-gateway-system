package com.demo.mathmicroservice1.service;

import com.demo.mathmicroservice1.model.MultiplicationResult;

/**
 * Interface that implement one method for the service.
 */
public interface MathService {

  /**
   * Interface method to vbe implemented take two integers operands and return an object MultiplicationResult
   *
   * @param firstOperand first integer operand
   * @param secondOperand second integer operand
   * @return return an object of type MultiplicationResult which hold the result of the operation along with port of
   * this service instance and the name of the service
   */
  MultiplicationResult multiply(int firstOperand, int secondOperand);
}
