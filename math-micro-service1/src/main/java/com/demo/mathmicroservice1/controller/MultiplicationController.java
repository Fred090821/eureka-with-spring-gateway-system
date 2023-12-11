package com.demo.mathmicroservice1.controller;

import com.demo.mathmicroservice1.model.MultiplicationResult;
import com.demo.mathmicroservice1.model.Operands;
import com.demo.mathmicroservice1.service.MathService;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MultiplicationController {

  private final MathService service;

  public MultiplicationController(MathService service) {
    this.service = service;
  }

  /**
   * Controller is the entry point for this microservice it will pass the operands to the service that in turn will
   * process and return the result as MultiplicationResult This is a post method that received the operands in a request
   * body, then extract them to be multiplied.
   *
   * @param requestData is the first object holding the operands to be multiplied together
   * @return ResponseEntity<MultiplicationResult> and http code ok.
   */
  @PostMapping("/multiply")
  public ResponseEntity<MultiplicationResult> processPostData(@Valid @RequestBody Operands requestData) {
    try {
      return new ResponseEntity<>(service.multiply(Integer.parseInt(requestData.getKey1()),
          Integer.parseInt(requestData.getKey2())), HttpStatus.OK);
    } catch (NumberFormatException e) {
      log.debug("Number format exception occurred with operands {} and {}", requestData.getKey1(), requestData.getKey2());
      return new ResponseEntity<>(new MultiplicationResult(), HttpStatus.BAD_REQUEST);
    }

  }

}

