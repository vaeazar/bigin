package com.bigin.game.common.constant;

import java.util.Arrays;

/**
 * 몬스터 스테이터스 정보
 * WEEK, NORMAL, HARD 순으로 강해짐
 */
public enum MonsterStatPoint {
  WEEK_MONSTER("weekMonster", new String[]{"healthPoint", "damage", "defend", "attackSpeed"}, new double[]{60,25,0,1000})
  , NORMAL_MONSTER("normalMonster", new String[]{"healthPoint", "damage", "defend", "attackSpeed"}, new double[]{60,30,5,1000})
  , HARD_MONSTER("hardMonster", new String[]{"healthPoint", "damage", "defend", "attackSpeed"}, new double[]{60,40,10,1000})
  , FAIL("fail", new String[]{"healthPoint", "damage", "defend", "attackSpeed"}, new double[]{0,0,0,0})
  ;

  private final String monsterName;
  private final String[] monsterStat;
  private final double[] monsterValue;

  MonsterStatPoint(String monsterName, String[] monsterStat, double[] monsterValue){
    this.monsterName = monsterName;
    this.monsterStat = monsterStat;
    this.monsterValue = monsterValue;
  }

  public String getMonsterName() {
    return monsterName;
  }

  public String[] getStatName() {
    return monsterStat;
  }

  public double[] getStatValue() {
    return monsterValue;
  }

  public static MonsterStatPoint selection(String keys) {
    return Arrays.stream(MonsterStatPoint.values())
        .filter(status -> status.monsterName.equals(keys))
        .findFirst()
        .orElse(WEEK_MONSTER);
  }
}
