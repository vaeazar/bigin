package com.bigin.game.common.constant;

public enum Skills {
  HEAL("heal", 10, 0, 50, 0)
  , STEAM("steam", 20, 60, 0.2, 0)
  , GUARD("guard", 40, 60, 0.3, 0)
  , ILLUSION("illusion", 40, 60, 0.3, 0)
  , ANGER("anger", 40, 60, 0.5, 0.1)
  , INVINCIBLE("invincible", 100, 10, 0, 0)
  , RAPID("rapid", 100, 60, 5, 0)
  , FRENZY("frenzy", 100, 60, 5, 0)
  ;

  private final String skillName;
  private final int skillMagicPoint;
  private final int skillDuration;
  private final double skillIncreaseValue;
  private final double skillDecreaseValue;

  Skills(String skillName, int skillMagicPoint, int skillDuration, double skillIncreaseValue, double skillDecreaseValue){
    this.skillName = skillName;
    this.skillMagicPoint = skillMagicPoint;
    this.skillDuration = skillDuration;
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

  public double getSkillIncreaseValue() {
    return skillIncreaseValue;
  }

  public double getSkillDecreaseValue() {
    return skillDecreaseValue;
  }
}
