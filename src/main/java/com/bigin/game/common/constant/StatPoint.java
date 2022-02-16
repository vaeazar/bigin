package com.bigin.game.common.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum StatPoint {
  HEALTH_POINT("healthPoint", 200)
  , MAGIC_POINT("magicPoint", 200)
  , DAMAGE("damage", 20)
  , ATTACK_SPEED("attackSpeed", 500)
  , DEFEND("defend", 20)
  , AVOID("avoid", 30)
  , ELSE("else", 0)
  ;

  private final String statName;
  private final Double statValue;

  StatPoint(String statName, double statValue){
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

    for (StatPoint statPoint : StatPoint.values()) {
      tempHashMap.put(statPoint.getStatName(), statPoint.getStatValue());
    }
    return tempHashMap;
  }
}
