package com.bigin.game.domain;

import com.bigin.game.common.constant.Skills;
import com.bigin.game.common.constant.UserEtcStatPoint;
import com.bigin.game.common.constant.UserStatPoint;
import com.bigin.game.common.constant.Weapon;
import java.util.HashMap;
import lombok.Data;

@Data
public class Elf extends User {

  int potion;

  public Elf() {
    HashMap<String, Boolean> basicSkills = new HashMap<>();
    basicSkills.put(Skills.HEAL.getSkillName(), true);
    basicSkills.put(Skills.STEAM.getSkillName(), true);
    basicSkills.put(Skills.ILLUSION.getSkillName(), true);
    this.inActionSkills = new HashMap<>();
    this.skill = new HashMap<>();
    this.statPoint = UserStatPoint.enumToHashMap();
    this.originalStatPoint = UserStatPoint.enumToHashMap();
    this.skill = basicSkills;
    this.lastAttackTime = 0;
    this.tribe = UserEtcStatPoint.ELF;
    this.alive = true;
    this.level = 1;
    this.weapon = Weapon.HAND.getWeaponName();
    this.potion = UserEtcStatPoint.POTION_COUNT;
  }

  /**
   * 일정 레벨 도달 시 궁극기를 배움
   * @return
   */
  public int levelUp() {

    if (this.level == UserEtcStatPoint.ULTIMATE_LIMIT) {
      increaseLevel();
      this.skill.put(Skills.RAPID.getSkillName(), true);
    } else if (this.level < UserEtcStatPoint.ULTIMATE_LIMIT) {
      increaseLevel();
      this.potion = UserEtcStatPoint.POTION_COUNT;
    }
    return this.level;
  }

  /**
   * 엘프만의 특징 포션 사용 체력 회복
   * @return
   */
  public boolean usePotion() {

    if (this.potion > 0) {
      String healthPoint = UserStatPoint.HEALTH_POINT.getStatName();
      double originalHealthPoint = this.originalStatPoint.get(healthPoint);
      double nowHealthPoint = this.statPoint.get(healthPoint);
      nowHealthPoint = nowHealthPoint + (originalHealthPoint * UserEtcStatPoint.POTION_HEAL);

      if (nowHealthPoint > originalHealthPoint) {
        nowHealthPoint = originalHealthPoint;
      }
      this.statPoint.put(healthPoint, nowHealthPoint);
      this.potion--;
      return true;
    }
    return false;
  }
}
