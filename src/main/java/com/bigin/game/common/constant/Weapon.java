package com.bigin.game.common.constant;

import java.util.Arrays;

/**
 * 무기 정보
 * weaponName 무기 명
 * weaponUseAble 무기 종족
 * weaponIncreaseName 증가 될 스테이터스
 * weaponDecreaseName 감소 될 스테이터스
 * weaponIncreaseValue 증가 될 값
 * weaponDecreaseValue 감소 될 값
 */
public enum Weapon {
  HAND("hand", "all", new String[]{}, new String[]{}, new double[]{}, new double[]{})
  , SHORT_SWORD("shortSword", "human", new String[]{"damage"}, new String[]{}, new double[]{0.05}, new double[]{})
  , LONG_SWORD("longSword", "human", new String[]{"damage"}, new String[]{}, new double[]{0.1}, new double[]{})
  , SHORT_BOW("shortBow", "elf", new String[]{"attackSpeed"}, new String[]{}, new double[]{0.05}, new double[]{})
  , IRON_BOW("ironBow", "elf", new String[]{"attackSpeed"}, new String[]{}, new double[]{0.1}, new double[]{0.1})
  , SHORT_AXE("shortAxe", "orc", new String[]{"damage"}, new String[]{"attackSpeed"}, new double[]{0.1}, new double[]{0.05})
  , IRON_HAMMER("ironHammer", "orc", new String[]{"damage"}, new String[]{"attackSpeed"}, new double[]{0.2}, new double[]{0.1})
  ;

  private final String weaponName;
  private final String weaponUseAble;
  private final String[] weaponIncreaseName;
  private final String[] weaponDecreaseName;
  private final double[] weaponIncreaseValue;
  private final double[] weaponDecreaseValue;

  Weapon(String weaponName
      , String weaponUseAble
      , String[] weaponIncreaseName
      , String[] weaponDecreaseName
      , double[] weaponIncreaseValue
      , double[] weaponDecreaseValue){
    this.weaponName = weaponName;
    this.weaponUseAble = weaponUseAble;
    this.weaponIncreaseName = weaponIncreaseName;
    this.weaponDecreaseName = weaponDecreaseName;
    this.weaponIncreaseValue = weaponIncreaseValue;
    this.weaponDecreaseValue = weaponDecreaseValue;
  }

  public String getWeaponName() {
    return weaponName;
  }

  public String getWeaponUseAble() {
    return weaponUseAble;
  }

  public String[] getWeaponIncreaseName() {
    return weaponIncreaseName;
  }

  public String[] getWeaponDecreaseName() {
    return weaponDecreaseName;
  }

  public double[] getWeaponIncreaseValue() {
    return weaponIncreaseValue;
  }

  public double[] getWeaponDecreaseValue() {
    return weaponDecreaseValue;
  }

  public static Weapon selection(String keys) {
    return Arrays.stream(Weapon.values())
        .filter(status -> status.weaponName.equals(keys))
        .findFirst()
        .orElse(HAND);
  }
}
