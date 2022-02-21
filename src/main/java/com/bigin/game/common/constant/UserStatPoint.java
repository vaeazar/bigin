package com.bigin.game.common.constant;

import java.util.HashMap;

/**
 * 유저 스테이터스 정보
 * healthPoint 체력
 * magicPoint 마나
 * damage 공격력
 * attackSpeed 공격속도
 * defend 방어력
 * avoid 회피력
 */
public enum UserStatPoint {
  HEALTH_POINT("healthPoint", 200)
  , MAGIC_POINT("magicPoint", 200)
  , DAMAGE("damage", 20)
  , ATTACK_SPEED("attackSpeed", 500)
  , DEFEND("defend", 20)
  , AVOID("avoid", 30)
  ;

  private final String statName;
  private final Double statValue;

  UserStatPoint(String statName, double statValue){
    this.statName = statName;
    this.statValue = statValue;
  }

  public String getStatName() {
    return statName;
  }

  public double getStatValue() {
    return statValue;
  }

  public static HashMap<String, Double> enumToHashMap() {
    HashMap<String, Double> tempHashMap = new HashMap<>();

    for (UserStatPoint userStatPoint : UserStatPoint.values()) {
      tempHashMap.put(userStatPoint.getStatName(), userStatPoint.getStatValue());
    }
    return tempHashMap;
  }
}
