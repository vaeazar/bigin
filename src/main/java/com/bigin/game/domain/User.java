package com.bigin.game.domain;

import com.bigin.game.common.constant.Skills;
import com.bigin.game.common.constant.UserStatPoint;
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

  /**
   * 플레이어가 죽었다면 예외를 발생시켜 다른 기능을 막는다.
   */
  public void checkAlive() {
    if (!this.alive) {
      throw new UserDeadException();
    }
  }

  /**
   * 몬스터를 죽이면 레벨 업 모든 체력, 마나를 회복
   * @return
   */
  public int increaseLevel() {
    String healthPoint = UserStatPoint.HEALTH_POINT.getStatName();
    String magicPoint = UserStatPoint.MAGIC_POINT.getStatName();
    this.level++;
    this.statPoint.put(healthPoint, this.originalStatPoint.get(healthPoint));
    this.statPoint.put(magicPoint, this.originalStatPoint.get(magicPoint));
    return this.level;
  }

  /**
   * 스킬이 사용 가능한지 체크
   * @param skillName 스킬 명
   * @param skillMagicPoint 스킬에 사용되는 마나
   * @return
   */
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

  /**
   * 일정 확률로 회피
   * @return
   */
  public boolean avoidAttack() {
    double randomValue = Math.random() * 100;
    double avoid = this.statPoint.get("avoid");
    return avoid > randomValue;
  }

  /**
   * 스킬 사용 로직
   * 스케쥴러를 이용해 일정 시간 후에는 증가 된 값을 감소
   * @param skillName 스킬 명
   * @return
   */
  public boolean useSkill(String skillName) {
    boolean hasSkill = skill.get(skillName) != null && skill.get(skillName);
    Skills skills = Skills.selection(skillName);

    if (hasSkill && isValidSkill(skillName, skills.getSkillMagicPoint())) {
      skillActive(skills, false);

      if (skills.getSkillDuration() != 0) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {
          this.inActionSkills.remove(skillName);
          skillActive(skills, true);
        };
        executor.schedule(task, skills.getSkillDuration(), TimeUnit.SECONDS);
        executor.shutdown();
        this.inActionSkills.put(skillName, skills.getSkillDuration());
      }
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
      double attackSpeed = this.statPoint.get("attackSpeed") < 1 ? 1 : this.statPoint.get("attackSpeed");
      long attackDelay = (Math.round((1000.0 / attackSpeed) * 10)) * 100;
      this.lastAttackTime = now + attackDelay;
      monster.userAttackMonster(this);
      return true;
    } else {
      return false;
    }
  }

  /**
   * 몬스터가 반격 시 사용되는 메소드
   * @param monsterDamage 몬스터의 공격력
   * @return
   */
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

  /**
   * 무기 사용 시 사용되는 메소드
   * @param weaponName 무기 명
   * @param tribe 유저 종족
   * @return
   */
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

  /**
   * 무기에 따른 상태치 증감 적용
   * @param weapon 무기 정보
   * @param isWeaponDetach 무기 탈착 시 true
   */
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

  /**
   * 스킬에 따른 상태치 증감
   * @param skills 스킬 정보
   * @param isSkillEnd 스킬로 인한 상태 원상복구 YN
   */
  private void skillActive(Skills skills, boolean isSkillEnd) {

    for (int i = 0; i < skills.getSkillIncreaseName().length; i++) {
      String key = skills.getSkillIncreaseName()[i];
      Double value = skills.getSkillIncreaseValue()[i];
      double increaseValue = Math.round(this.originalStatPoint.get(key) * value);
      double limitValue = this.originalStatPoint.get(key);
      double nowValue = this.statPoint.get(key);

      if (skills.getSkillHasLimit() && (nowValue + increaseValue) > limitValue) {
        increaseValue = limitValue - nowValue;
      }

      if (isSkillEnd) {
        this.statPoint.put(key, nowValue - increaseValue);
      } else {
        this.statPoint.put(key, nowValue + increaseValue);
      }
    }

    for (int i = 0; i < skills.getSkillDecreaseName().length; i++) {
      String key = skills.getSkillDecreaseName()[i];
      Double value = skills.getSkillDecreaseValue()[i];
      double decreaseValue = Math.round(this.originalStatPoint.get(key) * value);
      double nowValue = this.statPoint.get(key);

      if (isSkillEnd) {
        this.statPoint.put(key, nowValue + decreaseValue);
      } else {
        this.statPoint.put(key, nowValue - decreaseValue);
      }
    }
  }
}
