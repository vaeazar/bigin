package com.bigin.game.domain;

import com.bigin.game.common.constant.Skills;
import com.bigin.game.common.constant.StatPoint;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Human extends User {

  public Human() {
    HashMap<String, Boolean> basicSkills = new HashMap<>();
    basicSkills.put(Skills.HEAL.getSkillName(), true);
    basicSkills.put(Skills.STEAM.getSkillName(), true);
    basicSkills.put(Skills.GUARD.getSkillName(), true);
    this.statPoint = StatPoint.enumToHashMap();
    this.originalStatPoint = StatPoint.enumToHashMap();
    this.skill = basicSkills;
    this.lastAttackTime = 0;
  }

  public void levelUp() {
    increaseLevel();

    if (this.level == 99) {
      this.skill.put(Skills.INVINCIBLE.getSkillName(), true);
    }
  }

  public void testMethod() {
    System.out.println("hello there!");
  }

  public boolean useGuard() {

    if (useSkill(Skills.GUARD.getSkillName(), Skills.GUARD.getSkillMagicPoint())) {
      this.status.put(Skills.GUARD.getSkillName(), Skills.GUARD.getSkillDuration());
      int increaseDefend = (int) Math.round(this.originalDefend * Skills.GUARD.getSkillIncreaseValue());
      this.defend += increaseDefend;
      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

      Runnable task = () -> {
        this.status.remove(Skills.GUARD.getSkillName());
        this.defend -= increaseDefend;
      };
      executor.schedule(task, Skills.GUARD.getSkillDuration(), TimeUnit.SECONDS);
      executor.shutdown();
      return true;
    } else {
      return false;
    }
  }

  public boolean useInvincible() {
    if (useSkill(Skills.INVINCIBLE.getSkillName(), Skills.INVINCIBLE.getSkillMagicPoint())) {
      this.status.put(Skills.INVINCIBLE.getSkillName(), Skills.INVINCIBLE.getSkillDuration());
      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

      Runnable task = () -> {
        this.status.remove(Skills.INVINCIBLE.getSkillName());
      };
      executor.schedule(task, Skills.INVINCIBLE.getSkillDuration(), TimeUnit.SECONDS);
      executor.shutdown();
      return true;
    } else {
      return false;
    }
  }
}
