package com.bigin.game.service;

import com.bigin.game.domain.Elf;
import com.bigin.game.domain.Monster;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ElfService {

  Elf elf;

  public void makeElf() {
    this.elf = new Elf();
  }

  public Elf getElf() {
    return this.elf;
  }

  public List<String> getSkillList() {
    return new ArrayList<>(elf.getSkill().keySet());
  }

  public String attackMonster(Monster monster) {
    if (elf.userAttack(monster)) {
      return "success";
    } else {
      return "fail";
    }
  }

  public int levelUp() {
    return elf.levelUp();
  }

  public boolean useSkill(String skillName) {
    return elf.useSkill(skillName);
  }

  public boolean useWeapon(String weaponName) {
    return elf.useWeapon(weaponName, elf.getTribe());
  }

  public boolean usePotion() {
    return elf.usePotion();
  }

  public boolean monsterAttackUser(double damage) {
    return elf.monsterAttackUser(damage);
  }
}
