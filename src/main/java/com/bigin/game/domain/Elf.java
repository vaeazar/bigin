package com.bigin.game.domain;

import com.bigin.game.common.constant.Skills;
import com.bigin.game.common.constant.StatPoint;
import java.util.HashMap;

public class Elf extends User {

  public Elf() {
    HashMap<String, Boolean> basicSkills = new HashMap<>();
    basicSkills.put(Skills.HEAL.getSkillName(), true);
    basicSkills.put(Skills.STEAM.getSkillName(), true);
    basicSkills.put(Skills.ILLUSION.getSkillName(), true);
    this.inActionSkills = new HashMap<>();
    this.skill = new HashMap<>();
    this.statPoint = StatPoint.enumToHashMap();
    this.originalStatPoint = StatPoint.enumToHashMap();
    this.skill = basicSkills;
    this.lastAttackTime = 0;
    this.tribe = "elf";
    this.alive = true;
    this.level = 1;
    this.weapon = "hand";
  }

  public int levelUp() {
    increaseLevel();

    if (this.level == 99) {
      this.skill.put(Skills.RAPID.getSkillName(), true);
    }
    return this.level;
  }

//  public boolean useIllusion() {
//
//    if (useSkill(Skills.ILLUSION.getSkillName(), Skills.ILLUSION.getSkillMagicPoint())) {
//      this.inActionSkills.put(Skills.ILLUSION.getSkillName(), Skills.ILLUSION.getSkillDuration());
//      int increaseAvoid = (int) Math.round(this.originalAvoid * Skills.ILLUSION.getSkillIncreaseValue());
//      this.avoid += increaseAvoid;
//      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//
//      Runnable task = () -> {
//        this.inActionSkills.remove(Skills.ILLUSION.getSkillName());
//        this.avoid -= increaseAvoid;
//      };
//      executor.schedule(task, Skills.ILLUSION.getSkillDuration(), TimeUnit.SECONDS);
//      executor.shutdown();
//      return true;
//    } else {
//      return false;
//    }
//  }
//
//  public boolean useRapid() {
//    if (useSkill(Skills.RAPID.getSkillName(), Skills.RAPID.getSkillMagicPoint())) {
//      this.inActionSkills.put(Skills.RAPID.getSkillName(), Skills.RAPID.getSkillDuration());
//      int increaseAttackSpeed = (int) Math.round(this.originalAttackSpeed * Skills.RAPID.getSkillIncreaseValue());
//      this.attackSpeed += increaseAttackSpeed;
//      this.lastAttackTime = 0;
//      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//
//      Runnable task = () -> {
//        this.inActionSkills.remove(Skills.RAPID.getSkillName());
//        this.attackSpeed -= increaseAttackSpeed;
//      };
//      executor.schedule(task, Skills.RAPID.getSkillDuration(), TimeUnit.SECONDS);
//      executor.shutdown();
//      return true;
//    } else {
//      return false;
//    }
//  }
}
