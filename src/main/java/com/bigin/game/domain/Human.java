package com.bigin.game.domain;

import com.bigin.game.common.constant.Skills;
import com.bigin.game.common.constant.UserEtcStatPoint;
import com.bigin.game.common.constant.UserStatPoint;
import com.bigin.game.common.constant.Weapon;
import java.util.HashMap;
import lombok.Data;

@Data
public class Human extends User {

  boolean ironWill;

  public Human() {
    HashMap<String, Boolean> basicSkills = new HashMap<>();
    basicSkills.put(Skills.HEAL.getSkillName(), true);
    basicSkills.put(Skills.STEAM.getSkillName(), true);
    basicSkills.put(Skills.GUARD.getSkillName(), true);
    this.inActionSkills = new HashMap<>();
    this.skill = new HashMap<>();
    this.statPoint = UserStatPoint.enumToHashMap();
    this.originalStatPoint = UserStatPoint.enumToHashMap();
    this.skill = basicSkills;
    this.lastAttackTime = 0;
    this.tribe = UserEtcStatPoint.HUMAN;
    this.alive = true;
    this.level = 1;
    this.weapon = Weapon.HAND.getWeaponName();
    this.ironWill = true;
  }

  /**
   * 일정 레벨 도달 시 궁극기를 배움
   * @return
   */
  public int levelUp() {

    if (this.level == UserEtcStatPoint.ULTIMATE_LIMIT) {
      increaseLevel();
      this.skill.put(Skills.INVINCIBLE.getSkillName(), true);
    } else if (this.level < UserEtcStatPoint.ULTIMATE_LIMIT) {
      increaseLevel();
      this.ironWill = true;
    }
    return this.level;
  }
}
