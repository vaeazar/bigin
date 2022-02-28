package com.bigin.game.service;

import com.bigin.game.domain.Elf;
import com.bigin.game.domain.Monster;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ElfService implements UserService {

  Elf elf;

  @Override
  public void makeUser() {
    this.elf = new Elf();
  }

  @Override
  public Elf getUser() {
    return this.elf;
  }

  @Override
  public List<String> getSkillList() {
    return new ArrayList<>(elf.getSkill().keySet());
  }

  @Override
  public String attackMonster(Monster monster) {
    if (elf.userAttack(monster)) {
      return "success";
    } else {
      return "fail";
    }
  }

  @Override
  public int levelUp() {
    return elf.levelUp();
  }

  @Override
  public boolean useSkill(String skillName) {
    return elf.useSkill(skillName);
  }

  @Override
  public boolean useWeapon(String weaponName) {
    return elf.useWeapon(weaponName, elf.getTribe());
  }

  public boolean usePotion() {
    return elf.usePotion();
  }

  @Override
  public boolean monsterAttackUser(double damage) {
    return elf.monsterAttackUser(damage);
  }
}
