package com.bigin.game.common.constant;

import java.util.Arrays;
import java.util.Optional;

public enum Skills {
  HEAL("heal", 10, 0, new String[]{"healthPoint"}, new String[]{}, new double[]{0.2}, new double[]{})
  , STEAM("steam", 20, 60, new String[]{"damage"}, new String[]{}, new double[]{0.2}, new double[]{})
  , GUARD("guard", 40, 60, new String[]{"defend"}, new String[]{}, new double[]{0.3}, new double[]{})
  , ILLUSION("illusion", 40, 60, new String[]{"avoid"}, new String[]{}, new double[]{0.3}, new double[]{})
  , ANGER("anger", 40, 60, new String[]{"damage"}, new String[]{"defend"}, new double[]{0.5}, new double[]{0.1})
  , INVINCIBLE("invincible", 100, 10, new String[]{"invincible"}, new String[]{}, new double[]{}, new double[]{})
  , RAPID("rapid", 100, 60, new String[]{"attackSpeed"}, new String[]{}, new double[]{5}, new double[]{})
  , FRENZY("frenzy", 100, 60, new String[]{"damage"}, new String[]{}, new double[]{5}, new double[]{})
  , FAIL("fail", 0, 0, new String[]{}, new String[]{}, new double[]{}, new double[]{})
  ;

  private final String skillName;
  private final int skillMagicPoint;
  private final int skillDuration;
  private final String[] skillIncreaseName;
  private final String[] skillDecreaseName;
  private final double[] skillIncreaseValue;
  private final double[] skillDecreaseValue;

  Skills(String skillName, int skillMagicPoint, int skillDuration, String[] skillIncreaseName, String[] skillDecreaseName, double[] skillIncreaseValue, double[] skillDecreaseValue){
    this.skillName = skillName;
    this.skillMagicPoint = skillMagicPoint;
    this.skillDuration = skillDuration;
    this.skillIncreaseName = skillIncreaseName;
    this.skillDecreaseName = skillDecreaseName;
    this.skillIncreaseValue = skillIncreaseValue;
    this.skillDecreaseValue = skillDecreaseValue;
  }

  public String getSkillName() {
    return skillName;
  }

  public int getSkillDuration() {
    return skillDuration;
  }

  public int getSkillMagicPoint() {
    return skillMagicPoint;
  }

  public String[] getSkillIncreaseName() {
    return skillIncreaseName;
  }

  public String[] getSkillDecreaseName() {
    return skillDecreaseName;
  }

  public double[] getSkillIncreaseValue() {
    return skillIncreaseValue;
  }

  public double[] getSkillDecreaseValue() {
    return skillDecreaseValue;
  }

  public static int selectMagicPoint(String keys) {
    Optional<Skills> tempSkills = Arrays.stream(Skills.values())
        .filter(status -> status.skillName.equals(keys))
        .findFirst();
    return tempSkills.map(Skills::getSkillMagicPoint).orElse(-1);
  }

  public static int selectDuration(String keys) {
    Optional<Skills> tempSkills = Arrays.stream(Skills.values())
        .filter(status -> status.skillName.equals(keys))
        .findFirst();
    return tempSkills.map(Skills::getSkillDuration).orElse(-1);
  }

  public static Skills selection(String keys) {
    return Arrays.stream(Skills.values())
        .filter(status -> status.skillName.equals(keys))
        .findFirst()
        .orElse(FAIL);
  }
}
