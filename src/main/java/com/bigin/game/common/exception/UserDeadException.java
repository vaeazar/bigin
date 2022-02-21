package com.bigin.game.common.exception;

/**
 * 유저 사망 시 발생하는 예외
 */
public class UserDeadException extends RuntimeException {

  public UserDeadException() {

  }

  UserDeadException(String message) {
    super(message);
  }
}
