package com.bigin.game.service;

import com.bigin.game.domain.Human;
import com.bigin.game.domain.Monster;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HumanService implements UserService {

  Human human;

  @Override
  public void makeUser() {
    this.human = new Human();
  }

  @Override
  public Human getUser() {
    return this.human;
  }

  @Override
  public List<String> getSkillList() {
    return new ArrayList<>(human.getSkill().keySet());
  }

  @Override
  public String attackMonster(Monster monster) {
    if (human.userAttack(monster)) {
      return "success";
    } else {
      return "fail";
    }
  }

  @Override
  public int levelUp() {
    return human.levelUp();
  }

  @Override
  public boolean useSkill(String skillName) {
    return human.useSkill(skillName);
  }

  @Override
  public boolean useWeapon(String weaponName) {
    return human.useWeapon(weaponName, human.getTribe());
  }

  @Override
  public boolean monsterAttackUser(double damage) {
    return human.monsterAttackUser(damage);
  }
}
