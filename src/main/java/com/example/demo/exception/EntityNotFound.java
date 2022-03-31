package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityNotFound extends Exception {
  private int status;
  private String message;

  public EntityNotFound(int status, String message) {
    super(message);
    this.status = status;
    this.message = message;
  }
}
