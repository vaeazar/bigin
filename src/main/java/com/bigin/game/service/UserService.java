package com.bigin.game.service;

import com.bigin.game.domain.Monster;
import com.bigin.game.domain.User;
import java.util.List;

public interface UserService {

  void makeUser();
  User getUser();
  List<String> getSkillList();
  String attackMonster(Monster monster);
  int levelUp();
  boolean useSkill(String skillName);
  boolean useWeapon(String weaponName);
  boolean monsterAttackUser(double damage);
}
