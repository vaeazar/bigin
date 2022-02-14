package com.bigin.game.domain;

import com.bigin.game.common.constant.Skills;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Orc extends User {

  public Orc() {
    HashMap<String, Boolean> basicSkills = new HashMap<>();
    basicSkills.put(Skills.HEAL.getSkillName(), true);
    basicSkills.put(Skills.STEAM.getSkillName(), true);
    basicSkills.put(Skills.ANGER.getSkillName(), true);
    this.skill = basicSkills;
  }

  public void levelUp() {
    increaseLevel();

    if (this.level == 99) {
      this.skill.put(Skills.FRENZY.getSkillName(), true);
    }
  }

  public boolean useAnger() {
    if (useSkill(Skills.ANGER.getSkillName(), Skills.ANGER.getSkillMagicPoint())) {
      this.status.put(Skills.ANGER.getSkillName(), Skills.ANGER.getSkillDuraion());
      int increaseDamage = (int) Math.round(this.originalDamage * Skills.ANGER.getSkillIncreaseValue());
      int decreaseDefend = (int) Math.round(this.originalDefend * Skills.ANGER.getSkillDecreaseValue());
      this.damage += increaseDamage;
      this.defend -= decreaseDefend;
      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

      Runnable task = () -> {
        this.status.remove(Skills.ANGER.getSkillName());
        this.damage -= increaseDamage;
        this.defend += decreaseDefend;
      };
      executor.schedule(task, Skills.ANGER.getSkillDuraion(), TimeUnit.SECONDS);
      executor.shutdown();
      return true;
    } else {
      return false;
    }
  }

  public boolean useFrenzy() {
    if (useSkill(Skills.FRENZY.getSkillName(), Skills.FRENZY.getSkillMagicPoint())) {
      this.status.put(Skills.FRENZY.getSkillName(), Skills.FRENZY.getSkillDuraion());
      int increaseDamage = (int) Math.round(this.originalDamage * Skills.FRENZY.getSkillIncreaseValue());
      this.damage += increaseDamage;
      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

      Runnable task = () -> {
        this.status.remove(Skills.FRENZY.getSkillName());
        this.damage -= increaseDamage;
      };
      executor.schedule(task, Skills.FRENZY.getSkillDuraion(), TimeUnit.SECONDS);
      executor.shutdown();
      return true;
    } else {
      return false;
    }
  }
}
