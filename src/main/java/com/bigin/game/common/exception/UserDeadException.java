package com.bigin.game.common.exception;

public class UserDeadException extends RuntimeException {

  public UserDeadException() {

  }

  UserDeadException(String message) {
    super(message);
  }
}
