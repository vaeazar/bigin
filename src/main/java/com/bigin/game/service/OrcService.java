package com.bigin.game.service;

import com.bigin.game.domain.Monster;
import com.bigin.game.domain.Orc;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrcService {

  Orc orc;

  public void makeOrc() {
    this.orc = new Orc();
  }

  public Orc getOrc() {
    return this.orc;
  }

  public List<String> getSkillList() {
    return new ArrayList<>(orc.getSkill().keySet());
  }

  public String attackMonster(Monster monster) {
    if (orc.userAttack(monster)) {
      return "success";
    } else {
      return "fail";
    }
  }

  public int levelUp() {
    return orc.levelUp();
  }

  public boolean useSkillRenew(String skillName) {
    return orc.useSkillRenew(skillName);
  }

  public boolean useWeapon(String weaponName) {
    return orc.useWeapon(weaponName, orc.getTribe());
  }

  public boolean monsterAttackUser(int damage) {
    return orc.monsterAttackUser(damage);
  }
}
