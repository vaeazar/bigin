package com.bigin.game.common.exception;

/**
 * 몬스터 사망 시 발생하는 예외
 */
public class MonsterDeadException extends RuntimeException {

  public MonsterDeadException() {

  }

  MonsterDeadException(String message) {
    super(message);
  }
}
