package com.bigin.game.domain;

import com.bigin.game.common.constant.Skills;
import com.bigin.game.common.constant.SkillsRenew;
import com.bigin.game.common.constant.StatPoint;
import com.bigin.game.common.constant.Weapon;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

@Data
public class User {

  protected int level;
  protected String weapon;
  protected HashMap<String, Double> statPoint;
  protected HashMap<String, Double> originalStatPoint;
  protected HashMap<String, Object> inActionSkills;
  protected HashMap<String, Boolean> skill;
  long lastAttackTime;
  protected boolean alive;
  protected String tribe;

  public int increaseLevel() {
    String healthPoint = StatPoint.HEALTH_POINT.getStatName();
    String magicPoint = StatPoint.MAGIC_POINT.getStatName();
    this.level++;
    this.statPoint.put(healthPoint, this.originalStatPoint.get(healthPoint));
    this.statPoint.put(magicPoint, this.originalStatPoint.get(magicPoint));
    return this.level;
  }

  public double increaseHealthPoint(double healPoint) {
    double healthPoint = this.statPoint.get("healthPoint");
    double originalHealthPoint = this.originalStatPoint.get("healthPoint");
    healthPoint += healPoint;

    if (healthPoint > originalHealthPoint) {
      healPoint = originalHealthPoint;
      this.statPoint.put("healthPoint", healPoint);
    }
    return healthPoint;
  }

  public boolean useSkill(String skillName, double skillMagicPoint) {
    double magicPoint = this.statPoint.get("magicPoint");
    if (skillMagicPoint <= 0) {
      return false;
    } else if (skillMagicPoint > magicPoint) {
      return false;
    } else if (ObjectUtils.isNotEmpty(this.inActionSkills.get(skillName))) {
      return false;
    } else {
      this.statPoint.merge("magicPoint", skillMagicPoint * -1, Double::sum);
    }
    return true;
  }

  public boolean avoidAttack() {
    double randomValue = Math.random() * 100;
    double avoid = this.statPoint.get("avoid");
    return avoid > randomValue;
  }

  public boolean useHeal() {

    if (useSkill(Skills.HEAL.getSkillName(), Skills.HEAL.getSkillMagicPoint())) {
      increaseHealthPoint(Skills.HEAL.getSkillIncreaseValue());
      return true;
    } else {
      return false;
    }
  }

  public boolean useSkillRenew(String skillName) {
    SkillsRenew skillsRenew = SkillsRenew.selection(skillName);

    if (useSkill(skillName, skillsRenew.getSkillMagicPoint())) {
      this.inActionSkills.put(skillName, skillsRenew.getSkillDuration());
      skillActive(skillsRenew, false);
      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

      Runnable task = () -> {
        this.inActionSkills.remove(skillName);
        skillActive(skillsRenew, true);
      };
      executor.schedule(task, skillsRenew.getSkillDuration(), TimeUnit.SECONDS);
      executor.shutdown();
      return true;
    } else {
      return false;
    }
  }

//  public boolean useSteam() {
//    if (useSkill(Skills.STEAM.getSkillName(), Skills.STEAM.getSkillMagicPoint())) {
//      this.inActionSkills.put(Skills.STEAM.getSkillName(), Skills.STEAM.getSkillDuration());
//      int increaseDamage = (int) Math.round(this.originalDamage * Skills.STEAM.getSkillIncreaseValue());
//      this.damage += increaseDamage;
//      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//
//      Runnable task = () -> {
//        this.inActionSkills.remove(Skills.STEAM.getSkillName());
//        this.damage -= increaseDamage;
//      };
//      executor.schedule(task, Skills.STEAM.getSkillDuration(), TimeUnit.SECONDS);
//      executor.shutdown();
//      return true;
//    } else {
//      return false;
//    }
//  }

  public boolean userAttack(Monster monster) {
    long now = System.currentTimeMillis();

    if (now > this.lastAttackTime) {
      double attackSpeed = this.statPoint.get("attackSpeed");
      long attackDelay = (long) (Math.round((1000.0 / attackSpeed) * 10) / 10.0);
      this.lastAttackTime = now + (attackDelay * 1000);
      monster.userAttackMonster(this);
      return true;
    } else {
      return false;
    }
  }

  public boolean monsterAttackUser(double monsterDamage) {
    double healthPoint = this.statPoint.get("healthPoint");
    double defend = this.statPoint.get("defend");
    int realDamage = (int) Math.max(monsterDamage - defend, 0);

    if (ObjectUtils.isEmpty(this.inActionSkills.get(SkillsRenew.INVINCIBLE.getSkillName())) && !avoidAttack()) {
      healthPoint -= realDamage;
      this.statPoint.put("healthPoint",healthPoint);

      if (healthPoint <= 0) {
        this.alive = false;
      }
      return this.alive;
    }
    return true;
  }

  public boolean useWeapon(String weaponName, String tribe) {
    Weapon pastWeapon = Weapon.selection(this.weapon);
    Weapon weapon = Weapon.selection(weaponName);
    weaponActive(pastWeapon, true);

    if (weapon.getWeaponUseAble().equals(tribe)) {
      weaponActive(pastWeapon, false);
      this.weapon = weaponName;
    } else {
      this.weapon = Weapon.HAND.getWeaponName();
      return false;
    }
    return true;
  }

  private void weaponActive(Weapon weapon, boolean isWeaponDetach) {

    for (int i = 0; i < weapon.getWeaponIncreaseName().length; i++) {
      String key = weapon.getWeaponIncreaseName()[i];
      Double value = weapon.getWeaponIncreaseValue()[i];
      double resultValue = Math.round(this.originalStatPoint.get(key) * value);
      double nowValue = this.statPoint.get(key);

      if (isWeaponDetach) {
        this.statPoint.put(key, nowValue - resultValue);
      } else {
        this.statPoint.put(key, nowValue + resultValue);
      }
    }

    for (int i = 0; i < weapon.getWeaponDecreaseName().length; i++) {
      String key = weapon.getWeaponDecreaseName()[i];
      Double value = weapon.getWeaponDecreaseValue()[i];
      double resultValue = Math.round(this.originalStatPoint.get(key) * value);
      double nowValue = this.statPoint.get(key);

      if (isWeaponDetach) {
        this.statPoint.put(key, nowValue + resultValue);
      } else {
        this.statPoint.put(key, nowValue - resultValue);
      }
    }
  }

  private void skillActive(SkillsRenew skillsRenew, boolean isSkillEnd) {

    for (int i = 0; i < skillsRenew.getSkillIncreaseName().length; i++) {
      String key = skillsRenew.getSkillIncreaseName()[i];
      Double value = skillsRenew.getSkillIncreaseValue()[i];
      double resultValue = Math.round(this.originalStatPoint.get(key) * value);
      double nowValue = this.statPoint.get(key);

      if (isSkillEnd) {
        this.statPoint.put(key, nowValue - resultValue);
      } else {
        this.statPoint.put(key, nowValue + resultValue);
      }
    }

    for (int i = 0; i < skillsRenew.getSkillDecreaseName().length; i++) {
      String key = skillsRenew.getSkillDecreaseName()[i];
      Double value = skillsRenew.getSkillDecreaseValue()[i];
      double resultValue = Math.round(this.originalStatPoint.get(key) * value);
      double nowValue = this.statPoint.get(key);

      if (isSkillEnd) {
        this.statPoint.put(key, nowValue + resultValue);
      } else {
        this.statPoint.put(key, nowValue - resultValue);
      }
    }
  }
}
