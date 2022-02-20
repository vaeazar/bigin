package com.bigin.game.domain;

import com.bigin.game.common.constant.Skills;
import com.bigin.game.common.constant.StatPoint;
import com.bigin.game.common.constant.Weapon;
import com.bigin.game.common.exception.UserDeadException;
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

  public void checkAlive() {
    if (!this.alive) {
      throw new UserDeadException();
    }
  }

  public int increaseLevel() {
    String healthPoint = StatPoint.HEALTH_POINT.getStatName();
    String magicPoint = StatPoint.MAGIC_POINT.getStatName();
    this.level++;
    this.statPoint.put(healthPoint, this.originalStatPoint.get(healthPoint));
    this.statPoint.put(magicPoint, this.originalStatPoint.get(magicPoint));
    return this.level;
  }

  public boolean isValidSkill(String skillName, double skillMagicPoint) {
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

  public boolean useSkill(String skillName) {
    boolean hasSkill = skill.get(skillName) != null && skill.get(skillName);
    Skills skills = Skills.selection(skillName);

    if (hasSkill && isValidSkill(skillName, skills.getSkillMagicPoint())) {
      this.inActionSkills.put(skillName, skills.getSkillDuration());
      skillActive(skills, false);
      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

      Runnable task = () -> {
        this.inActionSkills.remove(skillName);
        skillActive(skills, true);
      };
      executor.schedule(task, skills.getSkillDuration(), TimeUnit.SECONDS);
      executor.shutdown();
      return true;
    } else {
      return false;
    }
  }

  /**
   * 초당 몇 회 공격할지 정해야한다.
   * 소수점 둘째자리에서 반올림 처리.
   * ex) 1초 = 1000, 500이면 1초당 0.5회 공격 따라서 2초에 한번씩 공격
   * @param monster
   * @return
   */
  public boolean userAttack(Monster monster) {
    long now = System.currentTimeMillis();

    if (now > this.lastAttackTime) {
      double attackSpeed = this.statPoint.get("attackSpeed");
      long attackDelay = (Math.round((1000.0 / attackSpeed) * 10)) * 100;
      this.lastAttackTime = now + attackDelay;
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

    if (ObjectUtils.isEmpty(this.inActionSkills.get(Skills.INVINCIBLE.getSkillName())) && !avoidAttack()) {
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
      weaponActive(weapon, false);
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

  private void skillActive(Skills skills, boolean isSkillEnd) {

    for (int i = 0; i < skills.getSkillIncreaseName().length; i++) {
      String key = skills.getSkillIncreaseName()[i];
      Double value = skills.getSkillIncreaseValue()[i];
      double resultValue = Math.round(this.originalStatPoint.get(key) * value);
      double nowValue = this.statPoint.get(key);

      if (isSkillEnd) {
        this.statPoint.put(key, nowValue - resultValue);
      } else {
        this.statPoint.put(key, nowValue + resultValue);
      }
    }

    for (int i = 0; i < skills.getSkillDecreaseName().length; i++) {
      String key = skills.getSkillDecreaseName()[i];
      Double value = skills.getSkillDecreaseValue()[i];
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
