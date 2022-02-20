package com.bigin.game.service;

import com.bigin.game.domain.Human;
import com.bigin.game.domain.Monster;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HumanService {

  Human human;

  public void makeHuman() {
    this.human = new Human();
  }

  public Human getHuman() {
    return this.human;
  }

  public List<String> getSkillList() {
    return new ArrayList<>(human.getSkill().keySet());
  }

  public String attackMonster(Monster monster) {
    if (human.userAttack(monster)) {
      return "success";
    } else {
      return "fail";
    }
  }

  public int levelUp() {
    return human.levelUp();
  }

  public boolean useSkill(String skillName) {
    return human.useSkill(skillName);
  }

  public boolean useWeapon(String weaponName) {
    return human.useWeapon(weaponName, human.getTribe());
  }

  public boolean monsterAttackUser(double damage) {
    return human.monsterAttackUser(damage);
  }
}
