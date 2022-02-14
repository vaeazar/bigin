package com.bigin.game.common.constant;

import java.util.Arrays;

public enum Weapon {
  HAND("hand", 0, 0, 0, 0, 0, 0, "all")
  , SHORT_SWORD("shortSword", 0.05, 0, 0, 0, 0, 0, "human")
  , LONG_SWORD("longSword", 0.1, 0, 0, 0, 0, 0, "human")
  , SHORT_BOW("shortBow", 0, 0, 0, 0,  0.05, 0,"elf")
  , IRON_BOW("ironBow", 0, 0, 0, 0, 0.1, 0, "elf")
  , SHORT_AXE("shortAxe", 0.1, 0, 0, 0, 0, 0.05, "orc")
  , IRON_HAMMER("ironHammer", 0.2, 0, 0, 0, 0, 0.1, "orc")
  ;

  private final String weaponName;
  private final double increaseAttack;
  private final double decreaseAttack;
  private final double increaseDefend;
  private final double decreaseDefend;
  private final double increaseAttackSpeed;
  private final double decreaseAttackSpeed;
  private final String weaponUseAble;

  Weapon(String weaponName
      , double increaseAttack
      , double decreaseAttack
      , double increaseDefend
      , double decreaseDefend
      , double increaseAttackSpeed
      , double decreaseAttackSpeed
      , String weaponUseAble){
    this.weaponName = weaponName;
    this.increaseAttack = increaseAttack;
    this.decreaseAttack = decreaseAttack;
    this.increaseDefend = increaseDefend;
    this.decreaseDefend = decreaseDefend;
    this.increaseAttackSpeed = increaseAttackSpeed;
    this.decreaseAttackSpeed = decreaseAttackSpeed;
    this.weaponUseAble = weaponUseAble;
  }

  public String getWeaponName() {
    return weaponName;
  }

  public double getIncreaseAttack() {
    return increaseAttack;
  }

  public double getDecreaseAttack() {
    return decreaseAttack;
  }

  public String getWeaponUseAble() {
    return weaponUseAble;
  }

  public double getIncreaseDefend() {
    return increaseDefend;
  }

  public double getDecreaseDefend() {
    return decreaseDefend;
  }

  public double getIncreaseAttackSpeed() {
    return increaseAttackSpeed;
  }

  public double getDecreaseAttackSpeed() {
    return decreaseAttackSpeed;
  }

  public static Weapon selection(String keys) {
    return Arrays.stream(Weapon.values())
        .filter(status -> status.weaponName.equals(keys))
        .findFirst()
        .orElse(HAND);
  }
}
