package com.bigin.game.domain;

import com.bigin.game.common.constant.Skills;
import com.bigin.game.common.constant.StatPoint;
import java.util.HashMap;

public class Human extends User {

  public Human() {
    HashMap<String, Boolean> basicSkills = new HashMap<>();
    basicSkills.put(Skills.HEAL.getSkillName(), true);
    basicSkills.put(Skills.STEAM.getSkillName(), true);
    basicSkills.put(Skills.GUARD.getSkillName(), true);
    this.inActionSkills = new HashMap<>();
    this.skill = new HashMap<>();
    this.statPoint = StatPoint.enumToHashMap();
    this.originalStatPoint = StatPoint.enumToHashMap();
    this.skill = basicSkills;
    this.lastAttackTime = 0;
    this.tribe = "human";
    this.alive = true;
    this.level = 1;
    this.weapon = "hand";
  }

  public int levelUp() {
    increaseLevel();

    if (this.level == 99) {
      this.skill.put(Skills.INVINCIBLE.getSkillName(), true);
    }
    return this.level;
  }

  public void testMethod() {
    System.out.println("hello there!");
  }

//  public boolean useGuard() {
//
//    if (useSkill(Skills.GUARD.getSkillName(), Skills.GUARD.getSkillMagicPoint())) {
//      this.inActionSkills.put(Skills.GUARD.getSkillName(), Skills.GUARD.getSkillDuration());
//      int increaseDefend = (int) Math.round(this.originalDefend * Skills.GUARD.getSkillIncreaseValue());
//      this.defend += increaseDefend;
//      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//
//      Runnable task = () -> {
//        this.inActionSkills.remove(Skills.GUARD.getSkillName());
//        this.defend -= increaseDefend;
//      };
//      executor.schedule(task, Skills.GUARD.getSkillDuration(), TimeUnit.SECONDS);
//      executor.shutdown();
//      return true;
//    } else {
//      return false;
//    }
//  }
//
//  public boolean useInvincible() {
//    if (useSkill(Skills.INVINCIBLE.getSkillName(), Skills.INVINCIBLE.getSkillMagicPoint())) {
//      this.inActionSkills.put(Skills.INVINCIBLE.getSkillName(), Skills.INVINCIBLE.getSkillDuration());
//      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//
//      Runnable task = () -> {
//        this.inActionSkills.remove(Skills.INVINCIBLE.getSkillName());
//      };
//      executor.schedule(task, Skills.INVINCIBLE.getSkillDuration(), TimeUnit.SECONDS);
//      executor.shutdown();
//      return true;
//    } else {
//      return false;
//    }
//  }
}
