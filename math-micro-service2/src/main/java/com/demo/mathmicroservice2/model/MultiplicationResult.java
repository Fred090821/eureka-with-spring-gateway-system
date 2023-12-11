package com.demo.mathmicroservice2.model;

import java.io.Serializable;

/**
 * Model for this microservice used to hold the data including the port of this instance and name of this service
 * registered with Eureka.
 */
public class MultiplicationResult implements Serializable {

  // result of the operation applied on the two provided operands
  private int result;
  // port uniquely identifying this instance
  private int instancePort;
  //the name of this service shared by the instances of this service
  private String instanceName;

  public MultiplicationResult() {
  }
  public MultiplicationResult(int result, int instancePort, String instanceName) {
    this.result = result;
    this.instancePort = instancePort;
    this.instanceName = instanceName;
  }

  public int getResult() {
    return result;
  }

  public void setResult(int result) {
    this.result = result;
  }

  public int getInstancePort() {
    return instancePort;
  }

  public void setInstancePort(int instancePort) {
    this.instancePort = instancePort;
  }

  public String getInstanceName() {
    return instanceName;
  }

  public void setInstanceName(String instanceName) {
    this.instanceName = instanceName;
  }

}