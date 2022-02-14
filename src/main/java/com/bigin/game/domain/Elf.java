package com.bigin.game.domain;

import com.bigin.game.common.constant.Skills;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Elf extends User {

  public Elf() {
    HashMap<String, Boolean> basicSkills = new HashMap<>();
    basicSkills.put(Skills.HEAL.getSkillName(), true);
    basicSkills.put(Skills.STEAM.getSkillName(), true);
    basicSkills.put(Skills.ILLUSION.getSkillName(), true);
    this.skill = basicSkills;
  }

  public void levelUp() {
    increaseLevel();

    if (this.level == 99) {
      this.skill.put(Skills.RAPID.getSkillName(), true);
    }
  }

  public boolean useIllusion() {

    if (useSkill(Skills.ILLUSION.getSkillName(), Skills.ILLUSION.getSkillMagicPoint())) {
      this.status.put(Skills.ILLUSION.getSkillName(), Skills.ILLUSION.getSkillDuraion());
      int increaseAvoid = (int) Math.round(this.originalAvoid * Skills.ILLUSION.getSkillIncreaseValue());
      this.avoid += increaseAvoid;
      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

      Runnable task = () -> {
        this.status.remove(Skills.ILLUSION.getSkillName());
        this.avoid -= increaseAvoid;
      };
      executor.schedule(task, Skills.ILLUSION.getSkillDuraion(), TimeUnit.SECONDS);
      executor.shutdown();
      return true;
    } else {
      return false;
    }
  }

  public boolean useRapid() {
    if (useSkill(Skills.RAPID.getSkillName(), Skills.RAPID.getSkillMagicPoint())) {
      this.status.put(Skills.RAPID.getSkillName(), Skills.RAPID.getSkillDuraion());
      int increaseAttackSpeed = (int) Math.round(this.originalAttackSpeed * Skills.RAPID.getSkillIncreaseValue());
      this.attackSpeed += increaseAttackSpeed;
      this.lastAttackTime = 0;
      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

      Runnable task = () -> {
        this.status.remove(Skills.RAPID.getSkillName());
        this.attackSpeed -= increaseAttackSpeed;
      };
      executor.schedule(task, Skills.RAPID.getSkillDuraion(), TimeUnit.SECONDS);
      executor.shutdown();
      return true;
    } else {
      return false;
    }
  }
}
