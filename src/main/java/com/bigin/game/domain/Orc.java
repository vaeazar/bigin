package com.bigin.game.domain;

import com.bigin.game.common.constant.Skills;
import com.bigin.game.common.constant.UserEtcStatPoint;
import com.bigin.game.common.constant.UserStatPoint;
import com.bigin.game.common.constant.Weapon;
import java.util.HashMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Orc extends User {

  int berserk;

  public Orc() {
    HashMap<String, Boolean> basicSkills = new HashMap<>();
    basicSkills.put(Skills.HEAL.getSkillName(), true);
    basicSkills.put(Skills.STEAM.getSkillName(), true);
    basicSkills.put(Skills.ANGER.getSkillName(), true);
    this.inActionSkills = new HashMap<>();
    this.skill = new HashMap<>();
    this.statPoint = UserStatPoint.enumToHashMap();
    this.originalStatPoint = UserStatPoint.enumToHashMap();
    this.skill = basicSkills;
    this.lastAttackTime = 0;
    this.tribe = UserEtcStatPoint.ORC;
    this.alive = true;
    this.level = 1;
    this.weapon = Weapon.HAND.getWeaponName();
    this.berserk = UserEtcStatPoint.BERSERK_COUNT;
  }

  /**
   * 일정 레벨 도달 시 궁극기를 배움
   * @return
   */
  public int levelUp() {

    if (this.level == UserEtcStatPoint.ULTIMATE_LIMIT) {
      increaseLevel();
      this.skill.put(Skills.FRENZY.getSkillName(), true);
    } else if (this.level < UserEtcStatPoint.ULTIMATE_LIMIT) {
      increaseLevel();
      this.berserk = UserEtcStatPoint.BERSERK_COUNT;
    }
    return this.level;
  }

  /**
   * 오크만의 특징 더 강한 공격
   * @return
   */
  public boolean useBerserk(boolean increase) {

    if (this.berserk > 0) {
      String damage = UserStatPoint.DAMAGE.getStatName();
      double originalDamage = this.originalStatPoint.get(damage);
      double nowDamage = this.statPoint.get(damage);

      if (increase) {
        nowDamage = nowDamage + (originalDamage * UserEtcStatPoint.BERSERK_DAMAGE);
      } else {
        nowDamage = nowDamage - (originalDamage * UserEtcStatPoint.BERSERK_DAMAGE);
      }
      this.statPoint.put(damage, nowDamage);
      this.berserk--;
      return true;
    }
    return false;
  }
}
