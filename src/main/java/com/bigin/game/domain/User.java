package com.bigin.game.domain;

import com.bigin.game.common.constant.Skills;
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
    this.level++;
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

    if (skillMagicPoint > this.magicPoint) {
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

  public boolean useSteam() {
    if (useSkill(Skills.STEAM.getSkillName(), Skills.STEAM.getSkillMagicPoint())) {
      this.status.put(Skills.STEAM.getSkillName(), Skills.STEAM.getSkillDuraion());
      int increaseDamage = (int) Math.round(this.originalDamage * Skills.STEAM.getSkillIncreaseValue());
      this.damage += increaseDamage;
      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

      Runnable task = () -> {
        this.status.remove(Skills.STEAM.getSkillName());
        this.damage -= increaseDamage;
      };
      executor.schedule(task, Skills.STEAM.getSkillDuraion(), TimeUnit.SECONDS);
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
}
