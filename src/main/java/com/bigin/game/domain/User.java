package com.bigin.game.domain;

import com.bigin.game.common.constant.Skills;
import com.bigin.game.common.constant.SkillsRenew;
import com.bigin.game.common.constant.StatPoint;
import com.bigin.game.common.constant.Weapon;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

@Data
public class User {

  protected int level;
  protected int originalHealthPoint;
  protected int originalMagicPoint;
  protected int originalDamage;
  protected double originalAttackSpeed;
  protected int originalDefend;
  protected double originalAvoid;
  protected String weapon;
  protected HashMap<String, Double> statPoint;
  protected HashMap<String, Double> originalStatPoint;
  protected HashMap<String, Object> status;
  protected HashMap<String, Boolean> skill;
  long lastAttackTime;

  protected int healthPoint;
  protected int magicPoint;
  protected int damage;
  protected double attackSpeed;
  protected int defend;
  protected double avoid;
  protected boolean alive;

  public int increaseLevel() {
    String healthPoint = StatPoint.HEALTH_POINT.getStatName();
    String magicPoint = StatPoint.MAGIC_POINT.getStatName();
    this.level++;
    this.statPoint.put(healthPoint, this.originalStatPoint.get(healthPoint));
    this.statPoint.put(magicPoint, this.originalStatPoint.get(magicPoint));
    return this.level;
  }

  public int increaseHealthPoint(double healPoint) {
    this.healthPoint += healPoint;

    if (this.healthPoint > this.originalHealthPoint) {
      this.healthPoint = this.originalHealthPoint;
    }
    return this.healthPoint;
  }

  public boolean useSkill(String skillName, int skillMagicPoint) {

    if (skillMagicPoint <= 0) {
      return false;
    } else if (skillMagicPoint > this.magicPoint) {
      return false;
    } else if (ObjectUtils.isNotEmpty(this.status.get(skillName))) {
      return false;
    } else {
      this.magicPoint -= skillMagicPoint;
    }
    return true;
  }

  public boolean avoidAttack() {
    double randomValue = Math.random() * 100;
    return this.avoid > randomValue;
  }

  public boolean useHeal() {

    if (useSkill(Skills.HEAL.getSkillName(), Skills.HEAL.getSkillMagicPoint())) {
      increaseHealthPoint(Skills.HEAL.getSkillIncreaseValue());
      return true;
    } else {
      return false;
    }
  }

  public boolean useSkill(String skillName) {
    SkillsRenew skillsRenew = SkillsRenew.selection(skillName);
    if (useSkill(skillName, skillsRenew.getSkillMagicPoint())) {
      this.status.put(skillName, skillsRenew.getSkillDuration());
      skillActive(skillsRenew, false);
      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

      Runnable task = () -> {
        this.status.remove(Skills.STEAM.getSkillName());
        skillActive(skillsRenew, true);
      };
      executor.schedule(task, Skills.STEAM.getSkillDuration(), TimeUnit.SECONDS);
      executor.shutdown();
      return true;
    } else {
      return false;
    }
  }

  public boolean useSteam() {
    if (useSkill(Skills.STEAM.getSkillName(), Skills.STEAM.getSkillMagicPoint())) {
      this.status.put(Skills.STEAM.getSkillName(), Skills.STEAM.getSkillDuration());
      int increaseDamage = (int) Math.round(this.originalDamage * Skills.STEAM.getSkillIncreaseValue());
      this.damage += increaseDamage;
      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

      Runnable task = () -> {
        this.status.remove(Skills.STEAM.getSkillName());
        this.damage -= increaseDamage;
      };
      executor.schedule(task, Skills.STEAM.getSkillDuration(), TimeUnit.SECONDS);
      executor.shutdown();
      return true;
    } else {
      return false;
    }
  }

  public int userAttack(Monster monster) {
    long now = System.currentTimeMillis();

    if (now > this.lastAttackTime) {
      long attackDelay = (long) (Math.round((1000.0 / attackSpeed) * 10) / 10.0);
      this.lastAttackTime = now + attackDelay;
      monster.userAttackMonster(this);
      return 1;
    } else {
      return -1;
    }
  }

  public boolean monsterAttackUser(int monsterDamage) {
    int realDamage = Math.max(monsterDamage - this.defend, 0);

    if (ObjectUtils.isNotEmpty(this.status.get(Skills.INVINCIBLE.getSkillName())) && !avoidAttack()) {
      this.healthPoint -= realDamage;

      if (this.healthPoint <= 0) {
        this.alive = false;
      }
      return this.alive;
    }
    return true;
  }

  public boolean useWeapon(String weaponName, String userKind, Field field) {
    Weapon pastWeapon = Weapon.selection(this.weapon);
    Weapon weapon = Weapon.selection(weaponName);
    this.damage -= (int) Math.round(this.originalDamage * pastWeapon.getIncreaseAttack());
    this.damage += (int) Math.round(this.originalDamage * pastWeapon.getDecreaseAttack());
    this.defend -= (int) Math.round(this.originalDefend * pastWeapon.getIncreaseDefend());
    this.defend += (int) Math.round(this.originalDefend * pastWeapon.getDecreaseDefend());
    this.attackSpeed -= (int) Math.round(this.originalAttackSpeed * pastWeapon.getIncreaseAttackSpeed());
    this.attackSpeed += (int) Math.round(this.originalAttackSpeed * pastWeapon.getDecreaseAttackSpeed());

    if (weapon.getWeaponUseAble().equals(userKind)) {
      this.damage += (int) Math.round(this.originalDamage * weapon.getIncreaseAttack());
      this.damage -= (int) Math.round(this.originalDamage * weapon.getDecreaseAttack());
      this.defend += (int) Math.round(this.originalDefend * weapon.getIncreaseDefend());
      this.defend -= (int) Math.round(this.originalDefend * weapon.getDecreaseDefend());
      this.attackSpeed += (int) Math.round(this.originalAttackSpeed * weapon.getIncreaseAttackSpeed());
      this.attackSpeed -= (int) Math.round(this.originalAttackSpeed * weapon.getDecreaseAttackSpeed());
    } else {
      this.weapon = Weapon.HAND.getWeaponName();
      return false;
    }
    return true;
  }

  private void skillActive(SkillsRenew skillsRenew, boolean isSkillEnd) {

    for (int i = 0; i < skillsRenew.getSkillIncreaseName().length; i++) {
      String key = skillsRenew.getSkillIncreaseName()[i];
      Double value = skillsRenew.getSkillIncreaseValue()[i];
      double resultValue = Math.round(this.originalStatPoint.get(key) * value);
      double nowValue = (double) this.status.get(key);

      if (isSkillEnd) {
        this.status.put(key, nowValue - resultValue);
      } else {
        this.status.put(key, nowValue + resultValue);
      }
    }

    for (int i = 0; i < skillsRenew.getSkillDecreaseName().length; i++) {
      String key = skillsRenew.getSkillDecreaseName()[i];
      Double value = skillsRenew.getSkillDecreaseValue()[i];
      double resultValue = Math.round(this.originalStatPoint.get(key) * value);
      double nowValue = (double) this.status.get(key);

      if (isSkillEnd) {
        this.status.put(key, nowValue + resultValue);
      } else {
        this.status.put(key, nowValue - resultValue);
      }
    }
  }
}
